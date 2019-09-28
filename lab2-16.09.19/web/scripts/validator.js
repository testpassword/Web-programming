"use strict";

let x, y, r;

// Обновляет значение x в соответсвии с нажатой кнопкой, добавляет ей эффекты (подсветка и увеличение), убирая их для остальных кнопок группы.
document.addEventListener("DOMContentLoaded", () => {
    let buttons = document.querySelectorAll("input[name=X-button]");
    buttons.forEach(click);
    drawCanvas();

    function click(element) {
        element.onclick = function () {
            x = this.value;
            buttons.forEach(function (element) {
                element.style.boxShadow = null;
                element.style.backgroundColor = null;
                element.style.color = null;
            });
            this.style.boxShadow = "0 0 40px 5px #f41c52";
            this.style.backgroundColor = "#f41c52";
            this.style.color = "white";
        }
    }

    function drawCanvas() {
        let canvas = document.querySelector("canvas").getContext("2d");
        canvas.font = "15px 'Avenir Next Cyr'";
        canvas.beginPath();
        //треугольник по OX
        canvas.moveTo(300, 150);
        canvas.lineTo(295, 155);
        canvas.lineTo(295, 145);
        canvas.closePath();
        //треуголник по OY
        canvas.moveTo(150, 0);
        canvas.lineTo(145, 5);
        canvas.lineTo(155, 5);
        canvas.closePath();
        //треугольник ОДЗ
        canvas.moveTo(90, 150);
        canvas.lineTo(150, 90);
        canvas.lineTo(150, 150);
        canvas.closePath();
        //прямоугольник ОДЗ
        canvas.fillRect(150, 150, 120, 60);
        //окружность ОДЗ
        canvas.moveTo(150, 150);
        canvas.lineTo(270, 150);
        canvas.bezierCurveTo(270, 40, 180, 30, 150, 30);
        canvas.closePath();
        //OX
        canvas.moveTo(0, 150);
        canvas.lineTo(300, 150);
        //OY
        canvas.moveTo(150, 300);
        canvas.lineTo(150, 0);
        //черты и подписи
        canvas.moveTo(270, 146);
        canvas.lineTo(270, 154);
        canvas.fillText("R", 265, 140);
        canvas.moveTo(210, 146);
        canvas.lineTo(210, 154);
        canvas.fillText("R/2", 200, 140);
        canvas.moveTo(90, 146);
        canvas.lineTo(90, 154);
        canvas.fillText("-R/2", 75, 140);
        canvas.moveTo(30, 146);
        canvas.lineTo(30, 154);
        canvas.fillText("-R", 20, 140);
        canvas.moveTo(146, 30);
        canvas.lineTo(154, 30);
        canvas.fillText("R", 156, 35);
        canvas.moveTo(146, 90);
        canvas.lineTo(154, 90);
        canvas.fillText("R/2", 156, 95);
        canvas.moveTo(146, 210);
        canvas.lineTo(154, 210);
        canvas.fillText("-R/2", 156, 215);
        canvas.moveTo(146, 270);
        canvas.lineTo(154, 270);
        canvas.fillText("-R", 156, 275);
        canvas.stroke();
    }
});

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()) {
        fetch("app", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
            body: "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
                "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone)
        }).then(response => response.text()).then(function (serverAnswer) {
            //setPointer(2 * (x * 54 / r) + 150, -(((y * 54 * 2) / r) - 150));
            document.getElementById("outputContainer").innerHTML = serverAnswer;
        }).catch(err => createNotification("Ошибка HTTP. Повторите попытку позже." + err));
    }
};

/*function setPointer(x, y) {
    let pointer = document.getElementById("pointer");
    pointer.style.visibility = "visible";
    pointer.setAttribute("cx", x); // 1:54 - масштаб, +150 позволяет вести отсёт от 0,0
    pointer.setAttribute("cy", y);
}*/

function createNotification(message) {
    let outputContainer = document.getElementById("outputContainer");
    if (outputContainer.contains(document.querySelector(".notification"))) {
        let stub = document.querySelector(".notification");
        stub.textContent = message;
        stub.classList.replace("outputStub", "errorStub");
    } else {
        let notificationTableRow = document.createElement("h4");
        notificationTableRow.innerHTML = "<span class='notification errorStub'></span>";
        outputContainer.prepend(notificationTableRow);
        let span = document.querySelector(".notification");
        span.textContent = message;
    }
}

function validateX() {
    if (isNumeric(x)) return true;
    else {
        createNotification("x не выбран");
        return false;
    }
}

function validateY() {
    y = document.querySelector("input[name=Y-input]").value.replace(",", "."); //замена разделителя дробной части числа
    if (y === undefined) {
        createNotification("y не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("y не число");
        return false;
    } else if (!((y > -5) && (y < 3))) {
        createNotification("y не входит в область допустимых значений");
        return false;
    } else return true;
}

function validateR() {
    try {
        r = document.querySelector("input[type=radio]:checked").value;
        return true;
    } catch (err) {
        createNotification("Значение R не выбрано");
        return false;
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

//TODO дополнить скрипт согласно варианту задания