"use strict";

var x, y, r;

document.getElementById("check-button").onclick = function () {
    if (checkX() && checkY && checkR()) alert("НОРМАЛЬНО");
}

window.onload = function () {
    let values = document.querySelectorAll("input[name=X-button]");
    for (let i = 0; i < values.length; i++) {
        values[i].onclick = function () {
            x = this.value;
        }
    }
}

function checkX() {
    if (!isNaN(x)) return true;
    else {
        alert("x не выбран");
        return false;
    }
}

function checkY() {
    console.log("dsfsf");
    y = document.getElementById("Y-input-form").value;
    if (!isNaN(y)) return true;
    else {
        alert("y не введён");
        return false;
    }
}

function checkR() {
    try {
        r = document.querySelector("input[type=\"radio\"]:checked").value;
        return true;
    }
    catch (err) {
        alert("Значение R не выбрано");
        return false;
    }
}

