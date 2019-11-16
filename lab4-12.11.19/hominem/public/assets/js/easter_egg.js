keyMapper(1000, "joke", function () {
    document.querySelectorAll("*").forEach((node) => node.classList.add("rotated"));
    const parent = document.getElementById("content");
    while (parent.firstChild) parent.firstChild.remove();
    const img = document.createElement("img");
    img.src = "/assets/img/joker.jpg";
    parent.appendChild(img);
});

function keyMapper(keystrokeDelay, keySequence, callback) {
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