"use strict";

let canvas = document.createElement("canvas");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
document.body.appendChild(canvas);

let keyboardControlsSettings = {
    up: "w",
    left: "a",
    down: "s",
    right: "d",
    newGame: "Enter",
    pause: " ",
};
let snakeSettings = {
    w: 10,
    h: 10,
    period: 300,
}
let viewSettings = {
    cellSize: 50,
    borderSize: 3,
    canvas,
}

let snake = new Snake();
let controls = new Controls();
let view = new CanvasView();

controls.setSnake(snake);
controls.setView(view);

controls.setViewSettings(viewSettings);
controls.setSettings(snakeSettings);
controls.createKeyboardControls(keyboardControlsSettings);

controls.newGame();