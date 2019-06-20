package com.example.olibe.myapplication;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;

public class Calculations {

    public static boolean[][][] solve(int[][][] gameBoard, int numberOfStrategies, EditText[][][] textFields) {
        // row, column, which player (0 is player 1, 1 is player 2)
        boolean[][][] maxStrategies = new boolean[numberOfStrategies][numberOfStrategies][2];

        //Player 0: compare rows in column
        for (int column = 0; column < numberOfStrategies; column++) {
            markMaxInColumn(gameBoard, numberOfStrategies, column, maxStrategies);
        }
        //Player 1: compare columns in row
        for (int row = 0; row < numberOfStrategies; row++) {
            markMaxInRow(gameBoard, numberOfStrategies, row, maxStrategies);
        }

        System.out.println("looking for equilibrium");
        boolean[][] equilibrium = new boolean[numberOfStrategies][numberOfStrategies];
        for (int row = 0; row < numberOfStrategies; row++) {
            for (int column = 0; column < numberOfStrategies; column++) {
                equilibrium [row][column]= maxStrategies[row][column][0] && maxStrategies[row][column][1];
                if(equilibrium [row][column]){
                    markEquilibrium(row, column, textFields);
                }
            }
        }
        return maxStrategies;
    }

    // 0 player chooses cell (row) from each column
    public static void markMaxInColumn(int[][][] gameBoard, int numberOfStrategies, int column, boolean[][][] maxStrategies) {
        int maximum = findColumnMax(gameBoard, numberOfStrategies, column);
        for (int row = 0; row < numberOfStrategies; row++) {
            if (maximum == gameBoard[row][column][0]) {
                System.out.println("one of player 0 max for column " + column + " is in row " + row + ": " + maximum);
                maxStrategies[row][column][0] = true;
            }
        }
    }

    public static int findColumnMax(int[][][] gameBoard, int numberOfStrategies, int column) {
        int currentMaximum = gameBoard[0][column][0];
        for (int row = 0; row < numberOfStrategies; row++) {
            if (currentMaximum <= gameBoard[row][column][0]) {
                currentMaximum = gameBoard[row][column][0];
            }
        }
        //System.out.println("max in column "+ column + " is "+currentMaximum);
        return currentMaximum;
    }

    // 1 player chooses cell (column) from each row
    public static void markMaxInRow(int[][][] gameBoard, int numberOfStrategies, int row, boolean[][][] maxStrategies) {
        int maximum = findRowMax(gameBoard, numberOfStrategies, row);
        for (int column = 0; column < numberOfStrategies; column++) {
            if (maximum == gameBoard[row][column][1]) {
                System.out.println("one of player 1 max for row " + row + " is in column " + column + ": " + maximum);
                maxStrategies[row][column][1] = true;
            }
        }
    }

    public static int findRowMax(int[][][] gameBoard, int numberOfStrategies, int row) {
        int currentMaximum = gameBoard[row][0][1];
        for (int column = 0; column < numberOfStrategies; column++) {
            if (currentMaximum <= gameBoard[row][column][1]) {
                currentMaximum = gameBoard[row][column][1];
            }
        }
        //System.out.println("max in row "+ row + " is "+currentMaximum);
        return currentMaximum;
    }

    public static void markEquilibrium(int row, int column, EditText[][][] textFields){
        blink(textFields[row][column][0]);
        blink(textFields[row][column][1]);

       // System.out.println("equilibrium found, row "+ row + " column "+ column);


    }

    private static void blink(EditText myEditText) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myEditText.startAnimation(anim);

    }
}
