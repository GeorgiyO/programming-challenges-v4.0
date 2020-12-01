"use strict";

const FTT_SIZE = 256;
const SVG_PROPS = {
    r: 300,

    circle: {
        radius: 75,
        width: 3,
    },

    barWMultiplier: 1,
    barAngle: 0,
    circleAngle: 135,
    dataLength: FTT_SIZE * 0.5,

    parentNode: document.getElementById("visualizer"),
}

let audioSources = document.getElementsByTagName("audio");
let handler = new AudioHandler();
let visualizer = new SvgVisualizer(SVG_PROPS);

let audio = document.getElementById("audio");
let source = document.getElementById("source");

source.onchange = () => {
    acceptFile();
}

Array.prototype.forEach.call(audioSources, (audio) => {
    audio.volume = 0.05;
    audio.onplay = () => {
        Array.prototype.forEach.call(audioSources, (audio2) => {
            if (audio === audio2) return;

            audio2.pause();
            audio2.currentTime = 0;
        });
        startAnimation(audio);
    }
});



visualizer.setAudioHandler(handler);

function startAnimation(audioSrc) {
    handler.setAudioSource(audioSrc, FTT_SIZE);
    visualizer.renderLoop();
}

function acceptFile() {
    audio.src = window.URL.createObjectURL(source.files[0]);
    audio.load();
}

function setDefaultAudio() {
    audio.src = "../audio/default.mp3";
    audio.load();
}