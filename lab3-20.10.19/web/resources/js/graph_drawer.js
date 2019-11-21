"use strict";

const svg = document.getElementById("graph");
const rect = document.getElementById("rect");
const triangle = document.getElementById("triangle");
const circle = document.getElementById("circle");
const target = document.getElementById("target");

document.addEventListener("DOMContentLoaded", () => {
    svg.addEventListener("click", (event) => {
        if (validateR()) {
            let position = getMousePosition(svg, event);
            x = position.x;
            y = position.y;
            setTarget("graph");
            sendRequest([{name:"X-value", value:x}, {name:"Y-value", value:y}, {name:"R-value", value:r}]);
            document.getElementById("footer").style.visibility = "hidden";
        }
    });
});

function getMousePosition(svg, event) {
    let rect = svg.getBoundingClientRect();
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    };
}

function redrawGraph() {
    rect.setAttribute("width", `${r * 24}`);
    rect.setAttribute("height", `${r * 12}`);
    rect.setAttribute("x", `${150 - r * 24}`);
    rect.setAttribute("y", `${150 - r * 12}`);
    triangle.setAttribute("points", `150,150 150,${150 - 24 * r} ${150 + 12 * r},150`);
    let k = (r !== 5) ? ((5 - r) * 12) : 0;
    circle.setAttribute("d", `M 150 150 L ${150 + r * 24} 150 C ${150 + r * 24} ${210 - k} ${210 - k} ${150 + r * 24} 150 ${150 + r * 24} Z`);
}

function setTarget(key) {
    const keys = ["button", "graph"];
    if (keys.includes(key)) {
        switch (key) {
            case "button":
                target.setAttribute("cx", `${x * 24 * r}`);
                target.setAttribute("cy", `${y * 12 * r}`);
                break;
            case "graph":
                target.setAttribute("cx", `${x}`);
                target.setAttribute("cy", `${y}`);
                x = (x / 24 / r).toFixed(1);
                y = (y / 12 / r).toFixed(1);
                break;
        }
        target.style.visibility = "visible";
        if ((x <= 0 && y >= 0 && x >= -r && y <= r/2) || (x >= 0 && y >= 0 && y <= (x - r/2) * (-2)) ||
            (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2))) target.setAttribute("fill", "green");
        else target.setAttribute("fill", "red");
    } else throw new Error("Не передан ключ функции");
}