class Snake {

    // int[][]
    field = [];
    w;
    h;
    period;
    intervalId;
    lost = false;

    // point queue
    snake = [];
    moveDir = MoveDir.right;
    newMoveDir = MoveDir.right;

    // functions

    onLoss;
    onWin;
    onStep;

    setOnLoss(onLoss) {
        this.onLoss = onLoss;
    }
    setOnWin(onWin) {
        this.onWin = onWin;
    }
    setOnStep(onStep) {
        this.onStep = onStep;
    }
    setMoveDir(dir) {
        if (this.moveDir !== MoveDir.getOpposite(dir))
            this.newMoveDir = dir;
    }

    newGame(w, h, period) {
        this.w = w;
        this.h = h;
        this.period = period;
        this.field = [];
        this.lost = false;
        this.snake = [];

        for (let i = 0; i < h; i++) {
            let row = [];
            for (let j = 0; j < w; j++) {
                row.push(FieldType.ground);
            }
            this.field.push(row);
            console.log(row + " ");
        }

        this.snake.push(new Point(Math.floor(w / 2), Math.floor(h / 2)));

        this.createRandApple();
        this.createRandApple();
        this.createRandApple();
    }

    play() {
        if (!this.lost) {
            this.doStep()
            this.intervalId = setInterval(() => {
                this.doStep()
            }, this.period);
        }
    }

    pause() {
        if (this.intervalId !== -1) {
            clearInterval(this.intervalId);
            this.intervalId = -1;
        }
    }

    // private:

    doStep() {
        if (this.moveDir !== this.newMoveDir) this.moveDir = this.newMoveDir;
        let newHead = this.getNewHead();
        if (this.isOutOfField(newHead) || this.field[newHead.x][newHead.y] === FieldType.snake)
            this.loseGame();
        else
            this.appendHead(newHead);
    }

    getNewHead() {
        let head = this.getSnakesHead();
        switch (this.moveDir) {
            case MoveDir.right: return new Point(head.x + 1, head.y);
            case MoveDir.left: return new Point(head.x - 1, head.y);
            case MoveDir.up: return new Point(head.x, head.y - 1);
            case MoveDir.down: return new Point(head.x, head.y + 1);
        }
    }

    isOutOfField(head) {
        return (
            head.x < 0 ||
            head.y < 0 ||
            head.x >= this.w ||
            head.y >= this.h
        );
    }

    loseGame() {
        this.onLoss();
        this.lost = true;
        this.pause();
    }

    appendHead(newHead) {
        this.snake.push(newHead);
        let grow = this.field[newHead.x][newHead.y] === FieldType.apple;
        if (grow) {
            this.createRandApple();
        } else {
            let p = this.snake.shift();
            this.field[p.x][p.y] = FieldType.ground;
        }
        this.field[newHead.x][newHead.y] = FieldType.snake;
        this.onStep(this.field);
    }

    createRandApple() {
        let x = AdvMath.getRandomInt(0, this.w - 1);
        let y = AdvMath.getRandomInt(0, this.h - 1);
        if (this.field[x][y] === FieldType.snake ||
            this.field[x][y] === FieldType.apple
            ) {
            this.createRandApple();
        } else {
            this.field[x][y] = FieldType.apple;
        }
    }

    getSnakesHead() {
        return this.snake[this.snake.length - 1];
    }

}
