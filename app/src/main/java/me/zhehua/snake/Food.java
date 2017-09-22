package me.zhehua.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * Created by zhehua on 13/09/2017.
 */

public class Food implements DrawableObj {
    Point position = new Point(33, 23);
    Paint paint = new Paint();
    Random random = new Random(System.currentTimeMillis());

    public Food() {
        paint.setColor(Color.RED);
    }

    public Point newPosition(int xBorder, int yBorder) {
        position = new Point(random.nextInt(xBorder), random.nextInt(yBorder));
        return position;
    }

    @Override
    public void draw(Canvas canvas, Scene scene) {
        canvas.drawRect(scene.transCoord(position), paint);
    }
}
