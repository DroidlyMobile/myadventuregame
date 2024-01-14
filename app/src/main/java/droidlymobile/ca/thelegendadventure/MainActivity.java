package droidlymobile.ca.thelegendadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import droidlymobile.ca.thelegendadventure.Objects.Objects;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    RelativeLayout gamelayout;
    LinearLayout dpadlayout;
    ImageView buttonRight,buttonLeft,buttonUp,buttonDown,buttonAttack;
    public int buttonwidth,buttonheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        fullscreen();
        /*gamelayout = findViewById(R.id.gamelayout);
        buttonRight = findViewById(R.id.buttonright);
        buttonLeft = findViewById(R.id.buttonleft);
        buttonUp = findViewById(R.id.buttonup);
        buttonDown = findViewById(R.id.buttondown);
        dpadlayout = findViewById(R.id.dpadlayout);
        buttonAttack = findViewById(R.id.buttonattack);
        gamelayout.addView(gameView);
        fullscreen();
        ontouchEvents();
        setupUI();*/
    }

    public void setupUI(){
        buttonwidth = gameView.getDisplayHeight()/6;
        buttonheight = gameView.getDisplayHeight()/6;
        viewWidth(buttonRight,buttonwidth);
        viewWidth(buttonLeft,buttonwidth);
        viewWidth(buttonUp,buttonwidth);
        viewWidth(buttonDown,buttonwidth);

        viewHeight(buttonRight,buttonheight);
        viewHeight(buttonLeft,buttonheight);
        viewHeight(buttonUp,buttonheight);
        viewHeight(buttonDown,buttonheight);
        viewHeight(buttonAttack,buttonheight);
        viewHeight(buttonAttack,buttonheight);

        viewWidth(dpadlayout,buttonwidth * 3);
        viewHeight(dpadlayout,buttonheight * 3);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void ontouchEvents() {
        buttonAttack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!gameView.player.entityAttacking) {
                            gameView.checkbuttonpressed = true;
                            gameView.player.entityAttacking = true;
                            gameView.player.entityDown = false;
                            gameView.player.entityUp = false;
                            gameView.player.entityRight = false;
                            gameView.player.entityLeft = false;
                            gameView.player.entityAnimCounter = 0;
                            gameView.player.entityAnimNum = 1;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
        buttonRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!gameView.player.entityAttacking){
                            gameView.checkbuttonpressed = true;
                            gameView.player.entityRight = true;
                            gameView.checkbutton = "right";
                        }
                        case MotionEvent.ACTION_MOVE:
                                if (!gameView.player.entityAttacking) {
                                    gameView.checkbuttonpressed = false;
                                    gameView.player.entityRight = false;
                                }
                                gameView.checkbutton = "none";
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!gameView.player.entityAttacking) {
                            gameView.checkbuttonpressed = false;
                            gameView.player.entityRight = false;
                        }
                        gameView.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
        buttonLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!gameView.player.entityAttacking){
                            gameView.checkbuttonpressed = true;
                            gameView.player.entityLeft = true;
                            gameView.checkbutton = "left";
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!gameView.player.entityAttacking) {
                            gameView.checkbuttonpressed = false;
                            gameView.player.entityLeft = false;
                        }
                        gameView.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
        buttonUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!gameView.player.entityAttacking){
                            gameView.checkbuttonpressed = true;
                            gameView.player.entityUp = true;
                            gameView.checkbutton = "up";
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!gameView.player.entityAttacking) {
                            gameView.checkbuttonpressed = false;
                            gameView.player.entityUp = false;
                        }
                        gameView.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
        buttonDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!gameView.player.entityAttacking){
                            gameView.checkbuttonpressed = true;
                            gameView.player.entityDown = true;
                            gameView.checkbutton = "down";
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!gameView.player.entityAttacking) {
                            gameView.checkbuttonpressed = false;
                            gameView.player.entityDown = false;
                        }
                        gameView.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.gameLoop.stopLoop();
    }
    public void fullscreen(){
        View decorView = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | Window.FEATURE_NO_TITLE|View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }
    public void viewWidth(View _view,int _width){
        _view.getLayoutParams().width = _width;
    }
    public void viewHeight(View _view,int _height){
        _view.getLayoutParams().height = _height;
    }
    public void _setViewWidthHeight(View _view, int _width,int _height) {
        _view.getLayoutParams().width = _width;
        _view.getLayoutParams().height = _height;
    }
}