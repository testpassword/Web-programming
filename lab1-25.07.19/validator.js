"use strict";

var x, y, r;
var pointer = document.getElementById("pointer");

//Добавляет подсветку на нажатую кнопку, и убирает её для остальных кнопок.
window.onload = function () {

    let buttons = document.querySelectorAll("input[name=X-button]");
    buttons.forEach(click);

    function click(element) {
        element.onclick = function () {
            x = this.value;
            buttons.forEach(function (element) {
                element.style.boxShadow = "";
            });
            this.style.boxShadow = "0 0 40px 5px #f41c52";
        }
    }
};

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()) sendRequest();
};

function sendRequest() {
    let response = fetch("answer.php", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
            "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone)
    });
    if (response.ok) {
        setPointer();
        constructTable(response.text());
    }
    else alert("Ошибка HTTP " + response.status);
}

function setPointer() {
    //TODO понять, на что вляет r
    pointer.style.visibility = "visible";
    pointer.setAttribute("cx", x * 54 + 150);
    pointer.setAttribute("cy", y * 54 + 150);
}

function constructTable(serverAnswer) {
    let outputContainer = document.getElementById("outputContainer");
    if (outputContainer.contains(document.getElementById("outputStub")))
        outputContainer.removeChild(outputContainer.firstElementChild);
    outputContainer.innerHTML = serverAnswer;
}

function validateX() {
    if (isNumeric(x)) return true;
    else {
        alert("x не выбран");
        return false;
    }
}

function validateY() {
    y = document.querySelector("input[name=Y-input-form]").value;
    if (isNumeric(y) && (y > -5) && (y < 3)) return true;
    else {
        alert("y не введён или введён некорректно");
        return false;
    }
}

function validateR() {
    try {
        r = document.querySelector("input[type=radio]:checked").value;
        return true;
    }
    catch (err) {
        alert("Значение R не выбрано");
        return false;
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}