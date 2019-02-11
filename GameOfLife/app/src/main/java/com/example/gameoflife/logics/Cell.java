package com.example.gameoflife.logics;

import android.graphics.Paint;
import android.util.Log;

public class Cell {
    private static final String TAG = "Cell";

    private State state;

    public Cell(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Paint getPaint() {
        Paint paint = new Paint();
        switch (state) {
            case ALIVE:
                Log.d(TAG, "getPaint: *****************************");
                paint.setARGB(255, 255, 255, 255);
                break;
            case DEAD:
                Log.d(TAG, "getPaint: -----------------------------");
                paint.setARGB(255, 0, 0, 0);
                break;
        }
        return paint;
    }
}
