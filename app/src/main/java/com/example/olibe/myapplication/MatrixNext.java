package com.example.olibe.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MatrixNext extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    private Button buttoncalculate;
    private Button buttonclear;

    private Button buttonnextpage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matrix_next);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.buttoncalculate).setOnTouchListener(mDelayHideTouchListener);

        buttonnextpage = (Button) findViewById(R.id.buttonnextpage);
        buttonnextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaving();
            }
        });


        buttoncalculate = (Button) findViewById(R.id.buttoncalculate);
        buttoncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solve();


            }
        });


        buttonclear = (Button) findViewById(R.id.buttonclear);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }

        });
    }

    public void openLeaving() {
        Intent intent = new Intent(this, LastBye.class);
        startActivity(intent);
    }


    private void clear() {
        EditText row0column0player0 = findViewById(R.id.row0column0);
        row0column0player0.getText().clear();
        row0column0player0.clearAnimation();
        row0column0player0.setAlpha(1.0f);

        EditText row0column0player1 = findViewById(R.id.row0column1);
        row0column0player1.getText().clear();
        row0column0player1.clearAnimation();
        row0column0player1.setAlpha(1.0f);

        EditText row0column1player0 = findViewById(R.id.row0column2);
        row0column1player0.getText().clear();
        row0column1player0.clearAnimation();
        row0column1player0.setAlpha(1.0f);

        EditText row0column1player1 = findViewById(R.id.row0column3);
        row0column1player1.getText().clear();
        row0column1player1.clearAnimation();
        row0column1player1.setAlpha(1.0f);

        EditText row1column0player0 = findViewById(R.id.row1column0);
        row1column0player0.getText().clear();
        row1column0player0.clearAnimation();
        row1column0player0.setAlpha(1.0f);

        EditText row1column0player1 = findViewById(R.id.row1column1);
        row1column0player1.getText().clear();
        row1column0player1.clearAnimation();
        row1column0player1.setAlpha(1.0f);

        EditText row1column1player0 = findViewById(R.id.row1column2);
        row1column1player0.getText().clear();
        row1column1player0.clearAnimation();
        row1column1player0.setAlpha(1.0f);

        EditText row1column1player1 = findViewById(R.id.row1column3);
        row1column1player1.getText().clear();
        row1column1player1.clearAnimation();
        row1column1player1.setAlpha(1.0f);
    }


    private void solve() {
        EditText row0column0player0 = findViewById(R.id.row0column0);
        int r0c0p0 = getThisOrDefault(row0column0player0);

        EditText row0column0player1 = findViewById(R.id.row0column1);
        int r0c0p1 = getThisOrDefault(row0column0player1);

        EditText row0column1player0 = findViewById(R.id.row0column2);
        int r0c1p0 = getThisOrDefault(row0column1player0);

        EditText row0column1player1 = findViewById(R.id.row0column3);
        int r0c1p1 = getThisOrDefault(row0column1player1);

        EditText row1column0player0 = findViewById(R.id.row1column0);
        int r1c0p0 = getThisOrDefault(row1column0player0);

        EditText row1column0player1 = findViewById(R.id.row1column1);
        int r1c0p1 = getThisOrDefault(row1column0player1);

        EditText row1column1player0 = findViewById(R.id.row1column2);
        int r1c1p0 = getThisOrDefault(row1column1player0);

        EditText row1column1player1 = findViewById(R.id.row1column3);
        int r1c1p1 = getThisOrDefault(row1column1player1);

// row, column, which player (0 is player 1, 1 is player 2)
        int[][][] gameBoard = new int[][][]{
                new int[][]{new int[]{r0c0p0, r0c0p1}, new int[]{r0c1p0, r0c1p1}},
                new int[][]{new int[]{r1c0p0, r1c0p1}, new int[]{r1c1p0, r1c1p1}}

        };

        EditText[][][] textFields = new EditText[][][]{
                new EditText[][]{new EditText[]{row0column0player0, row0column0player1}, new EditText[]{row0column1player0, row0column1player1}},
                new EditText[][]{new EditText[]{row1column0player0, row1column0player1}, new EditText[]{row1column1player0, row1column1player1}}
        };


        Calculations.solve(gameBoard, 2, textFields);

    }

    private int getThisOrDefault(EditText editText) {
        String value = editText.getText().toString();
        if (value.isEmpty()|| value.trim().length() == 0){
            //editText.setText('0');
            return 0;
        }
        else {
            int intValue = Integer.parseInt(value);
            return intValue;}

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
