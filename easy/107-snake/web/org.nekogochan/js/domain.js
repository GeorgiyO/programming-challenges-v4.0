class FieldType {
    static ground = 0;
    static snake = 1;
    static apple = 2;
}
class MoveDir {
    static up = 0;
    static right = 1;
    static down  = 2;
    static left = 3;

    static getOpposite(dir) {
        switch (dir) {
            case this.up: return this.down;
            case this.down: return this.up
            case this.left: return this.right;
            case this.right: return this.left;
        }
    }
}
class Point {
    x;
    y;

    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}