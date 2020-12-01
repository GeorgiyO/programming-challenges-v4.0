class AudioHandler {

    audioCtx;
    analyser;

    source;

    initAudioCtxAndAnalyser() {
        if (!this.audioCtx) {
            this.audioCtx = new window.AudioContext();
            this.analyser = this.audioCtx.createAnalyser();
        }
    }

    /**
     * @param audioStream - audio source file
     * @param fttSize - степень двойки, хуй знает на че влияет
     */
    setAudioSource(audioStream, fttSize) {
        this.initAudioCtxAndAnalyser();

        this.source = this.audioCtx.createMediaElementSource(audioStream);
        this.analyser.fftSize = fttSize;

        this.source.connect(this.analyser);
        this.source.connect(this.audioCtx.destination)
    }

    /**
     * @param data - Uint8Array
     */
    getData(data) {
        this.analyser.getByteFrequencyData(data);
    }

}