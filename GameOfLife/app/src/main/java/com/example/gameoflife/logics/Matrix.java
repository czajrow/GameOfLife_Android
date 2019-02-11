package com.example.gameoflife.logics;

import android.graphics.Paint;

import java.util.Random;

public class Matrix {
    private static final String TAG = "Matrix";

    private Cell[][] cells;
    public final int DIMENSION;

    public Matrix(int dimension) {
        this.DIMENSION = dimension;
        this.cells = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cells[i][j] = new Cell(State.DEAD);
            }
        }
    }

    public void randommize(double aliveFraction) {
        Random r = new Random();
        this.cells = new Cell[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                cells[i][j] = new Cell(r.nextDouble() < aliveFraction ? State.ALIVE : State.DEAD);
            }
        }
    }

    public void newGeneration() {
        Cell[][] newCells = new Cell[DIMENSION][DIMENSION];
        int x = DIMENSION, y = DIMENSION, x1, y1, x2, y2, n;
        for (y1 = 0; y1 < y; y1++)     // po kolumnach
            for (x1 = 0; x1 < x; x1++) { // po wierszach
                newCells[x1][y1] = new Cell(State.DEAD);
                n = 0;
                for (x2 = x1 - 1; x2 <= x1 + 1; x2++)   // sasiednie kolumny
                    for (y2 = y1 - 1; y2 <= y1 + 1; y2++) // sasiednie wiersze
                        if (cells[(x2 + x) % x][(y2 + y) % y].getState() == State.ALIVE)
                            n++;
                if (cells[x1][y1].getState() == State.ALIVE)
                    n--;

                if (n == 3 || (n == 2 && cells[x1][y1].getState() == State.ALIVE))
                    newCells[x1][y1].setState(State.ALIVE);
                else
                    newCells[x1][y1].setState(State.DEAD);
            }
        cells = newCells;
    }

    public Paint getPaint(int x, int y) {
        return cells[x][y].getPaint();
    }

    public void negState(int x, int y) {
        cells[x][y].negState();
    }
}
