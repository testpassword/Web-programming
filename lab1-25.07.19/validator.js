"use strict";

let x, y, r;

//Добавляет эффекты (подсветка и увеличение) на нажатую кнопку, убирает эффекты для остальных кнопок.
window.onload = function () {

    let buttons = document.querySelectorAll("input[name=X-button]");
    buttons.forEach(click);

    function click(element) {
        element.onclick = function () {
            x = this.value;
            buttons.forEach(function (element) {
                element.style.boxShadow = "";
                element.style.transform = "";
            });
            this.style.boxShadow = "0 0 40px 5px #f41c52";
            this.style.transform = "scale(1.05)";
        }
    }
};

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()) {
        fetch("answer.php", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"},
            body: "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
                "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone)
        }).then(response => response.text()).then(function (data) {
            setPointer();
            constructTable(data);
        }).catch(err => alert("Ошибка HTTP. Повторите попытку позже. " + err));
    }
};

function setPointer() {
    //TODO понять, на что вляет r
    let pointer = document.getElementById("pointer");
    pointer.style.visibility = "visible";
    pointer.setAttribute("cx", x * 54 + 150); // 1:54 - масштаб, +150 позволяет вести отсёт от 0,0
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
    y = document.querySelector("input[name=Y-input]").value;
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