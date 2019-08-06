"use strict";

var x, y, r;

document.getElementById("data").onsubmit = function () {
    if (validateX() && validateY() && validateR()) {
        let sender = new XMLHttpRequest();
        sender.open("POST", "answer.php", false);
        sender.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        sender.send("x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r));
        let answer = JSON.parse(sender.responseText);
    }
}

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
        r = document.querySelector("input[type=\"radio\"]:checked").value;
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