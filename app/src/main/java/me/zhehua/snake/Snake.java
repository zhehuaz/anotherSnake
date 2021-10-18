package me.zhehua.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.annotation.NonNull;

import java.util.LinkedList;

/**
 * Created by zhehua on 13/09/2017.
 */

public class Snake implements DrawableObj {

    LinkedList<Point> snakeBody = new LinkedList<>();
    Paint paint = new Paint();

    Direction faceTo;

    public Direction faceTo() {
        return faceTo;
    }

    public void grow(@NonNull Point newHead) {
        snakeBody.addFirst(newHead);
    }

    public Snake() {
        snakeBody.add(new Point(3,5));
        snakeBody.add(new Point(3,4));
        snakeBody.add(new Point(3,3));
        snakeBody.add(new Point(2,3));
        snakeBody.add(new Point(2,2));
        snakeBody.add(new Point(1,2));
        snakeBody.add(new Point(1,1));


        faceTo = Direction.DOWN;
        paint.setColor(Color.GREEN);
    }

    int speed = 5;

    public Point getHead() {
        return snakeBody.getFirst();
    }

    public void goTo(Direction direction) {
        switch (direction) {
            case UP:
                goUp();
                break;
            case DOWN:
                goDown();
                break;
            case LEFT:
                goLeft();
                break;
            case RIGHT:
                goRight();
                break;
            case NONE:
                goForward();
                break;
        }
    }

    public void goForward() {
        if (faceTo == Direction.NONE) {
            throw new IllegalStateException("Face to none direction");
        }
        goTo(faceTo);
    }

    public void goUp() {
        if (faceTo != Direction.DOWN) {
            Point head = snakeBody.getFirst();
            Point tail = snakeBody.removeLast();
            tail.set(head.x, head.y - 1);
            snakeBody.addFirst(tail);
            faceTo = Direction.UP;
        } else {
            goForward();
        }
    }

    public void goDown() {
        if (faceTo != Direction.UP) {
            Point head = snakeBody.getFirst();
            Point tail = snakeBody.removeLast();
            tail.set(head.x, head.y + 1);
            snakeBody.addFirst(tail);
            faceTo = Direction.DOWN;
        } else {
            goForward();
        }
    }

    public void goLeft() {
        if (faceTo != Direction.RIGHT) {
            Point head = snakeBody.getFirst();
            Point tail = snakeBody.removeLast();
            tail.set(head.x - 1, head.y);
            snakeBody.addFirst(tail);
            faceTo = Direction.LEFT;
        } else {
            goForward();
        }
    }

    public void goRight() {
        if (faceTo != Direction.LEFT) {
            Point head = snakeBody.getFirst();
            Point tail = snakeBody.removeLast();
            tail.set(head.x + 1, head.y);
            snakeBody.addFirst(tail);
            faceTo = Direction.RIGHT;
        } else {
            goForward();
        }
    }

    boolean isDead() {


        return false;
    }

    @Override
    public void draw(Canvas canvas, Scene scene) {
        for (Point point : snakeBody) {
            canvas.drawRect(scene.transCoord(point), paint);
        }
    }
}
