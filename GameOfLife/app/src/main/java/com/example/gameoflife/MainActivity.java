package com.example.gameoflife;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.gameoflife.logics.Matrix;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Matrix matrix = new Matrix(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SurfaceView surface = findViewById(R.id.surfaceView);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Do some drawing when surface is ready
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.RED);
                Paint paint = new Paint();
                paint.setARGB(255, 0, 0, 0);
                canvas.drawRect(0, 0, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
//                drawMatrix(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrix.newGeneration();
                Canvas canvas = surface.getHolder().lockCanvas();
                surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
            }
        });
    }

    private Canvas drawMatrix(Canvas canvas) {
        int h = canvas.getHeight() / matrix.DIMENSION;
        int w = canvas.getWidth() / matrix.DIMENSION;
        for (int x = 0; x < matrix.DIMENSION; x++) {
            for (int y = 0; y < matrix.DIMENSION; y++) {
                float left = x * w;
                float top = y * h;
                float right = left + w;
                float bottom = top + h;
                Paint paint = matrix.getPaint(x, y);
                canvas.drawRect(left, top, right, bottom, paint);
                Log.d(TAG, "drawMatrix: " + "---" + left + " " + top + " " + right + " " + bottom);
            }
        }
        return canvas;
    }
}
