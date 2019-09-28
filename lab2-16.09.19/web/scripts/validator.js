"use strict";

let x, y, r;
let canvas = document.querySelector("canvas");

// Обновляет значение x в соответсвии с нажатой кнопкой, добавляет ей эффекты (подсветка и увеличение), убирая их для остальных кнопок группы.
document.addEventListener("DOMContentLoaded", () => {
    let buttons = document.querySelectorAll("input[name=X-button]");
    buttons.forEach(click);
    canvas.addEventListener("click", (event) => {
        if (validateR()) {
            let field = canvas.getBoundingClientRect();
            let x = event.clientX - field.left;
            let y = event.clientY - field.top;
            setPointer(/*получение и расчёт координат*/);
            sendRequest();
        }
    });
    loadCanvasBackground();

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
});

function loadCanvasBackground() {
    let context = canvas.getContext("2d");
    let image = new Image();
    image.src = "images/graph.svg";
    image.onload = function() {
        context.drawImage(image, 0, 0);
    };
}

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()) sendRequest();
};

function sendRequest() {
    setPointer(2 * (x * 54 / r) + 150, -(((y * 54 * 2) / r) - 150));
    fetch("app", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
        body: "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
            "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone)
    }).then(response => response.text()).then(function (serverAnswer) {
        document.getElementById("outputContainer").innerHTML = serverAnswer;
    }).catch(err => createNotification("Ошибка HTTP. Повторите попытку позже." + err));
}

function setPointer(x, y) {
    let context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);
    loadCanvasBackground();
    context.beginPath();
    context.arc(x, y, 5, 0, 2* Math.PI);
    context.closePath();
    context.stroke();
    context.fillStyle = "red";
    context.fill();
}

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