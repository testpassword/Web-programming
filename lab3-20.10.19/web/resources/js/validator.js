"use strict";

let x, y, r;

//Обновляет значение x в соответсвии с нажатой кнопкой, добавляет ей эффекты (подсветка и увеличение), убирая их для остальных кнопок группы.
document.addEventListener("DOMContentLoaded", () => {
    let buttons = document.querySelectorAll("input[name=R-button]");
    buttons.forEach(click);

    function click(element) {
        element.onclick = function () {
            r = this.value;
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

function verify() {
    if (validateX() & validateY() & validateR()) {
        //TODO: правильно считать координаты точки
        document.querySelector("svg").insertAdjacentHTML("beforeend", `<circle r="5" cx="${x}" cy="${y}" fill-opacity="0.7" fill="red" stroke="firebrick"></circle>`);
        console.log(`x=${x} y=${y} r=${r}`);
        sendRequest([{name:"X-value", value:x}, {name:"Y-value", value:y}, {name:"R-value", value:r}]);
    }
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
}

//Позволяет получить значение x из нестандартного элемента p:oneSelectRadio
function reactToChangeXRadio(param) {
    x = param;
}

function validateY() {
    y = document.querySelector("#Y-input").value.replace(",", "."); //замена разделителя дробной части числа
    if (y === undefined) {
        createNotification("Y не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("Y не число");
        return false;
    } else if (!((y > -5) && (y < 3))) {
        createNotification("Y не входит в область допустимых значений");
        return false;
    } else return true;
}

function validateR() {
    if (isNumeric(r)) return true;
    else {
        createNotification("R не выбран");
        return false;
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}