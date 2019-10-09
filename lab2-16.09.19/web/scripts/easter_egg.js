"use strict";

document.addEventListener("DOMContentLoaded", () => {
    keyMapper(1000, () => document.querySelectorAll("*").forEach(function (node) {
        node.classList.add("rotated")}), "flex");
});

function keyMapper(keystrokeDelay, callback, keySequence) {
    let buffer = [];
    let lastKeyTime = Date.now();
    document.addEventListener("keydown", event => {
        let charList = "abcdefghijklmnopqrstuvwxyz0123456789";
        const key = event.key.toLowerCase();
        if (charList.indexOf(key) === -1) return;
        let currentTime = Date.now();
        if (currentTime - lastKeyTime > 1000) buffer = [];
        buffer.push(key);
        lastKeyTime = currentTime;
        if (buffer.join("") === keySequence) callback();
    })
}