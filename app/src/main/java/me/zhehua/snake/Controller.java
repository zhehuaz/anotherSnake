package me.zhehua.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static me.zhehua.snake.Direction.NONE;

public class Controller extends GestureDetector.SimpleOnGestureListener
        implements View.OnTouchListener {
    private static final String TAG = "Controller";
    private static final int CONTROLLER_R = 100;
    private GestureDetector gestureDetector;
    private boolean isDragging = false;
    private PointF dragDis = new PointF();
    private PointF pivot = new PointF();
    private Paint linePaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint dirPaint = new Paint();
    private Direction currentDir = NONE;
    private double theta;

    public void updateDir() {
        if (dragDis.x < 1e-5 && dragDis.y < 1e-5) {
            currentDir = Direction.NONE;
        }

        if (dragDis.x > 0) {
            theta = Math.atan(dragDis.y / dragDis.x);;
        } else if (dragDis.x < 0) {
            if (dragDis.y > 0) {
                theta = Math.PI + Math.atan(dragDis.y / dragDis.x);
            } else {
                theta = Math.atan(dragDis.y / dragDis.x) - Math.PI;
            }
        } else {
            if (dragDis.y > 0) {
                theta = Math.PI / 2;
            } else {
                theta = - Math.PI / 2;
            }
        }

        if (theta >= - Math.PI / 4 * 3 && theta < - Math.PI / 4) {
            currentDir = Direction.UP;
        } else if (theta >= - Math.PI / 4 && theta < Math.PI / 4) {
            currentDir = Direction.RIGHT;
        } else if (theta >= Math.PI / 4 && theta < Math.PI / 4 * 3) {
            currentDir = Direction.DOWN;
        } else {
            currentDir = Direction.LEFT;
        }

    }

    public Direction getCurDir() {
       return currentDir;
    }

    public Controller(Context context) {
        gestureDetector = new GestureDetector(context, this);
        linePaint.setColor(Color.WHITE);
        linePaint.setAntiAlias(true);

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);

        dirPaint.setColor(Color.WHITE);
        dirPaint.setAntiAlias(true);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        isDragging = true;
        pivot.set(e.getX(), e.getY());
        Log.i(TAG, "touch " + pivot.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "on show press");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        isDragging = true;
        dragDis.set(e2.getX() - e1.getX(), e2.getY() - e1.getY());
        //dragDis.offset(- distanceX, - distanceY);
        Log.i(TAG, String.format("touch dis %f %f", e2.getX() - e1.getX(), e2.getY() - e1.getY()));
        Log.i(TAG, String.format("drag dis %f %f", dragDis.x, dragDis.y));
//        Log.i(TAG, String.format("drag direction is %s", getCurDir().toString()));
        if (dragDis.x * dragDis.x + dragDis.y * dragDis.y > CONTROLLER_R * CONTROLLER_R) {
            updateDir();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.i(TAG, "touch position " + event.getY());
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i(TAG, "action up");
            isDragging = false;
            dragDis.set(0, 0);
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * In controller, coordinates by pixel in the screen.
     * @param canvas
     * @param control
     */
    public void draw(Canvas canvas, GameControl control) {
        if (isDragging) {
            canvas.save();
            canvas.scale(control.getScreenScaleX(), control.getScreenScaleY());

            float cord = (float) (CONTROLLER_R / Math.sqrt(2));
            canvas.drawLine(pivot.x + cord, pivot.y - cord, pivot.x - cord, pivot.y + cord, linePaint);
            canvas.drawLine(pivot.x + cord ,pivot.y + cord, pivot.x - cord, pivot.y - cord, linePaint);
            canvas.drawOval(pivot.x - CONTROLLER_R,
                    pivot.y - CONTROLLER_R,
                    pivot.x + CONTROLLER_R,
                    pivot.y + CONTROLLER_R, circlePaint);

            dirPaint.setColor(Color.WHITE);

            switch (currentDir) {
                case UP:
                    if (control.getGameScene().snake.faceTo() == Direction.DOWN) {
                        dirPaint.setColor(Color.RED);
                    }
                    canvas.drawArc(pivot.x - CONTROLLER_R,
                            pivot.y - CONTROLLER_R,
                            pivot.x + CONTROLLER_R,
                            pivot.y + CONTROLLER_R, 225f, 90f, true, dirPaint);
                    break;
                case DOWN:
                    if (control.getGameScene().snake.faceTo() == Direction.UP) {
                        dirPaint.setColor(Color.RED);
                    }
                    canvas.drawArc(pivot.x - CONTROLLER_R,
                            pivot.y - CONTROLLER_R,
                            pivot.x + CONTROLLER_R,
                            pivot.y + CONTROLLER_R, 45f, 90f, true, dirPaint);
                    break;
                case RIGHT:
                    if (control.getGameScene().snake.faceTo() == Direction.LEFT) {
                        dirPaint.setColor(Color.RED);
                    }
                    canvas.drawArc(pivot.x - CONTROLLER_R,
                            pivot.y - CONTROLLER_R,
                            pivot.x + CONTROLLER_R,
                            pivot.y + CONTROLLER_R, 315f, 90f, true, dirPaint);
                    break;
                case LEFT:
                    if (control.getGameScene().snake.faceTo() == Direction.RIGHT) {
                        dirPaint.setColor(Color.RED);
                    }
                    canvas.drawArc(pivot.x - CONTROLLER_R,
                            pivot.y - CONTROLLER_R,
                            pivot.x + CONTROLLER_R,
                            pivot.y + CONTROLLER_R, 135f, 90f, true, dirPaint);
                    break;
            }

            canvas.drawLine(pivot.x, pivot.y, pivot.x + dragDis.x, pivot.y + dragDis.y, linePaint);
            canvas.restore();
        }
    }

}
