class CanvasView {

    canvas;
    ctx;

    cellSize;
    borderSize;
    cellWithoutBorder;

    setSettings(settings) {
        this.canvas = settings.canvas;
        this.ctx = this.canvas.getContext("2d");

        this.cellSize = settings.cellSize;
        this.borderSize = settings.borderSize;
        this.cellWithoutBorder = this.cellSize - 2 * this.borderSize;
    }

    updateView(field) {
        field.forEach((row, x) => {
            row.forEach((cell, y) => {
                this._drawCell(cell, x, y);
            })
        })
    }

    _drawCell(cell, x, y) {
        this.ctx.fillStyle = this._getFillStyle(cell);
        this.ctx.fillRect(this.borderSize + x * this.cellSize,
            this.borderSize + y * this.cellSize,
            this.cellWithoutBorder, this.cellWithoutBorder);
    }

    _getFillStyle(cell) {
        switch (cell) {
            case FieldType.ground: return this._groundStyle();
            case FieldType.apple: return this._appleStyle();
            case FieldType.snake: return this._snakeStyle();
        }
    }

    _groundStyle() {
        return "black";
    }
    _appleStyle() {
        return "red";
    }
    _snakeStyle() {
        return "#00ff00";
    }

}