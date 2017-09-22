package me.zhehua.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * Created by zhehua on 13/09/2017.
 */

public class GameControl extends Thread {
    private static final String TAG = "GameControl";
    private static final int FIXED_WIDTH = 720;
    private static final int FIXED_HEIGHT = 1280;
    private SurfaceView sfGameView;
    private SurfaceHolder sfGameHolder;

    // scale of screen size to fixed size
    private float screenScaleX;
    private float screenScaleY;

    private Scene gameScene;
    private Controller controller;
    private boolean isGameRunning = false;

    public boolean isRunning() {
        return isGameRunning;
    }

    public void releaseGame() {
        isGameRunning = false;
    }

    public GameControl(Context context, SurfaceView sfGameView, SurfaceHolder sfGameHolder) {
        this.sfGameView = sfGameView;
        this.controller = new Controller(context);
        this.sfGameView.setOnTouchListener(controller);
        this.sfGameHolder = sfGameHolder;
        this.sfGameHolder.setFixedSize(FIXED_WIDTH, FIXED_HEIGHT);

        gameScene = new Scene(controller);
        gameScene.setHeight(FIXED_HEIGHT);
        gameScene.setWidth(FIXED_WIDTH);
    }

    public void setViewSize(int height, int width) {
        screenScaleX = (float)FIXED_WIDTH / width;
        screenScaleY = (float)FIXED_HEIGHT / height;
    }

    public float getScreenScaleX() {
        return screenScaleX;
    }

    public float getScreenScaleY() {
        return screenScaleY;
    }

    public Scene getGameScene() {
        return gameScene;
    }

    @Override
    public void run() {
        isGameRunning = true;
        while(isGameRunning) {
            Canvas canvas = sfGameHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                gameScene.draw(canvas);
                controller.draw(canvas, this);

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sfGameHolder.unlockCanvasAndPost(canvas);
            }
        }
    }



}
