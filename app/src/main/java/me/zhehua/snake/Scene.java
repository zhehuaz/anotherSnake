package me.zhehua.snake;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhehua on 13/09/2017.
 */

public class Scene {
    public Snake snake = new Snake();
    public Food food = new Food();
    public List<Carrier> carriers = new ArrayList<>();
    public ScoreBlock scoreBlock = new ScoreBlock();
    public Controller controller;

    public static final int[][] directionOffset = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {0, 0}};

    private int height;
    private int width;
    private int step = 20;

    public Scene(Controller controller) {
        carriers.add(new Carrier());
        this.controller = controller;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Rect transCoord(Point p) {
        int left, top;
        left = p.x * step;
        top = p.y * step;
        return new Rect(left, top, left + step, top + step);
    }

    private void boundRestrict(Point point) {
        if (point.y > height / step - 1) {
            point.y = 0;
        } else if (point.y < 0) {
            point.y = height / step - 1;
        }
        if (point.x > width / step - 1) {
            point.x = 0;
        } else if (point.x < 0) {
            point.x = width / step - 1;
        }
    }

    int drawIdx = 0;
    void draw(Canvas canvas) {
        if (drawIdx == snake.speed) {
            drawIdx = 0;
            Point snakeNextPos = new Point(snake.getHead());
            snakeNextPos.offset(directionOffset[controller.getCurDir().ordinal()][0],
                    directionOffset[controller.getCurDir().ordinal()][1]);

            // TODO what if snake's next pos is bounded?
            if (food.position.equals(snakeNextPos)) {
                snake.grow(food.position);
                food.newPosition(width / step, height / step);
            } else {
                snake.goTo(controller.getCurDir());
                boundRestrict(snake.getHead());
            }
        }
        snake.draw(canvas, this);

        food.draw(canvas, this);
        for (Carrier carrier : carriers) {
            carrier.draw(canvas, this);
        }
        scoreBlock.draw(canvas, this);

        drawIdx ++;
    }
}
