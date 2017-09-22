package me.zhehua.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by zhehua on 13/09/2017.
 */

public class Carrier implements DrawableObj{
    Point position = new Point(12, 19);
    Paint paint = new Paint();

    public Carrier() {
        paint.setColor(Color.GRAY);
    }

    @Override
    public void draw(Canvas canvas, Scene scene) {
        canvas.drawRect(scene.transCoord(position), paint);
    }
}
