package com.example.gameoflife;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.gameoflife.logics.Matrix;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int dimension = 10;
    private double aliveFraction = 0.2;

    Matrix matrix = new Matrix(dimension);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SurfaceView surface = findViewById(R.id.surfaceView);
        surface.performClick();
        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    int cellX = (int) map(x, 0, surface.getWidth(), 0, dimension);
                    int cellY = (int) map(y, 0, surface.getHeight(), 0, dimension);
                    matrix.negState(cellX, cellY);
                    Canvas canvas = surface.getHolder().lockCanvas();
                    surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
                }
                return true;
            }
        });

        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = surface.getHolder().lockCanvas();
                surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrix.newGeneration();
                Canvas canvas = surface.getHolder().lockCanvas();
                surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
            }
        });

        Button randButton = findViewById(R.id.randButton);
        randButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrix = new Matrix(dimension);
                matrix.randommize(aliveFraction);
                Canvas canvas = surface.getHolder().lockCanvas();
                surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
            }
        });

        Button newButton = findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matrix = new Matrix(dimension);
                Canvas canvas = surface.getHolder().lockCanvas();
                surface.getHolder().unlockCanvasAndPost(drawMatrix(canvas));
            }
        });
    }

    private Canvas drawMatrix(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        int h = canvas.getHeight() / matrix.DIMENSION;
        int w = canvas.getWidth() / matrix.DIMENSION;
        for (int x = 0; x < matrix.DIMENSION; x++) {
            for (int y = 0; y < matrix.DIMENSION; y++) {
                float left = x * w;
                float top = y * h;
                float right = left + w;
                float bottom = top + h;
                Paint paint = matrix.getPaint(x, y);
                canvas.drawRect(left, top, right - 1, bottom - 1, paint);
            }
        }
        return canvas;
    }

    private double map(double value, double min1, double max1, double min2, double max2) {
        if (value <= min1) {
            return min2;
        } else if (value >= max1) {
            return max2;
        } else {
            return ((value - min1) * (max2 - min2)) / (max1 - min1) + min2;
        }
    }
}
