package me.zhehua.snake;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private static final String TAG = "MainActivity";
    SurfaceView sfGameView;
    SurfaceHolder sfGameViewHolder;
    GameControl gameControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sfGameView = (SurfaceView) findViewById(R.id.sv_game);
        sfGameViewHolder = sfGameView.getHolder();
        sfGameViewHolder.addCallback(this);

        gameControl = new GameControl(this, sfGameView, sfGameViewHolder);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surface changed");
        if (gameControl.isRunning()) {
            gameControl.releaseGame();
        }
        gameControl.setViewSize(sfGameView.getHeight(), sfGameView.getWidth());
        gameControl.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameControl.releaseGame();
    }
}
