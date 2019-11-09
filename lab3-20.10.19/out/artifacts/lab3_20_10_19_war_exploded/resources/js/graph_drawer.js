"use strict";

const svg = document.getElementById("graph");
const rect = document.getElementById("rect");
const triangle = document.getElementById("triangle");
const circle = document.getElementById("circle");

document.addEventListener("DOMContentLoaded", () => {
    svg.addEventListener("click", (event) => {
        if (validateR()) {
            let position = getMousePosition(svg, event);
            x = position.x;
            y = position.y;
            target.style.visibility = "visible";
            target.setAttribute("cx", `${x}`);
            target.setAttribute("cy", `${y}`);
            let k = 270 / r; //отношение радиуса и плоскости
            x = (x / k).toFixed(1);
            y = (y / k).toFixed(1);
            if ((x <= 0 && y >= 0 && x >= -r && y <= r/2) || (x >= 0 && y >= 0 && y <= (x - r/2) * (-2)) ||
                (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2))) target.setAttribute("fill", "green");
            else target.setAttribute("fill", "red");
            sendRequest([{name:"X-value", value:x}, {name:"Y-value", value:y}, {name:"R-value", value:r}]);
            document.getElementById("outputContainer").remove();
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