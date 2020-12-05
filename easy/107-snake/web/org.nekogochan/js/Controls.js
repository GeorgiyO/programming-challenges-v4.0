class Controls {

    snake
    view;
    settings;

    playing;

    setSnake(snake) {
        this.snake = snake;
        this.snake.setOnLoss(() => {
            this.snake.pause();
            this.playing = false;
            alert("проебал")
        });
        this.snake.setOnWin(() => {
            this.snake.pause();
            this.playing = false;
            alert("выиграл")
        });
    }

    setView(view) {
        this.view = view;
        this.snake.setOnStep((field) => {
            view.updateView(field);
        })
    }

    setViewSettings(settings) {
        this.view.setSettings(settings);
    }

    setSettings(settings) {
        this.settings = settings;
    }

    createKeyboardControls(settings) {
        document.onkeydown = (e) => {
            let k = e.key;
            if (k === settings.newGame) {
                this.newGame();
            } else if (k === settings.pause) {
                this.pause();
            } else if (this.playing) {
                switch (k) {
                    case settings.up: this.setMoveDir(MoveDir.up); break;
                    case settings.left: this.setMoveDir(MoveDir.left); break;
                    case settings.down: this.setMoveDir(MoveDir.down); break;
                    case settings.right: this.setMoveDir(MoveDir.right); break;
                }
            }
        }
    }

    newGame() {
        let w = this.settings.w;
        let h = this.settings.h;
        let period = this.settings.period;
        this.snake.newGame(w, h, period);
        this.snake.pause();
        this.snake.play();
        this.playing = true;
    }

    pause() {
        this.playing = !this.playing;
        if (this.playing) {
            this.snake.play();
        } else {
            this.snake.pause()
        }
    }

    setMoveDir(dir) {
        this.snake.setMoveDir(dir);
    }
}