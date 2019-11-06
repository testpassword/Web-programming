"use strict";

const svg = document.getElementById("graph");

document.addEventListener("DOMContentLoaded", () => {
    svg.addEventListener("click", (event) => {
        if (validateR()) {
            let position = getMousePosition(svg, event);
            x = position.x;
            y = position.y;
            target.style.visibility = "visible";
            target.setAttribute("cx", x);
            target.setAttribute("cy", y);
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