"use strict";

var x, y, r;
var sender = new XMLHttpRequest();

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
    //TODO заменить XMLHttpRequest на fetch
    sender.open("POST", "answer.php", true);
    sender.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    sender.send("x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
        "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone));
    let pointer = document.getElementById("pointer");
    sender.onreadystatechange = function () {
        let answerElement = document.getElementById("output");
        if (sender.status == 200) {
            let answer = JSON.parse(sender.responseText);
            setPointer(pointer);
            answerElement.textContent ="x = " + x + " / y = " + y + " / r = " + r + " / Точка входит в ОДЗ = " +
                answer.coordsStatus + " / Текущее время = " + answer.currentTime + " / Время работы скрипта = " +
                answer.benchmarkTime;
        }
        else answerElement.textContent = "Ошибка HTTP " + sender.status;
    }
}

function setPointer(pointer) {
    //TODO понять, на что вляет r
    pointer.style.visibility = "visible";
    pointer.setAttribute("cx", x * 54 + 150);
    pointer.setAttribute("cy", y * 54 + 150);
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