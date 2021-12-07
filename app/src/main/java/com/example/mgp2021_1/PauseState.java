package com.example.mgp2021_1;

import android.content.Intent;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.UIBackground;

public class PauseState implements StateBase {

    private CustomButton btn_resume, btn_exit = null;
    private UIBackground pause_menu = null;

    public String GetName() {
        return "Pause";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        int ScreenHeight = metrics.heightPixels;
        int ScreenWidth = metrics.widthPixels;

        btn_resume = new CustomButton(R.drawable.resume_btn, 414, 102,
                ScreenWidth / 2, ScreenHeight - 600);
        btn_exit = new CustomButton(R.drawable.exit_btn, 414, 102,
                ScreenWidth / 2, ScreenHeight - 400);

        pause_menu = new UIBackground();
        pause_menu.SetBMP(R.drawable.ui_frame1);
        pause_menu.SetScale(600, 600);
        pause_menu.SetPos(ScreenWidth/2, ScreenHeight/2);
        pause_menu.Init(_view);
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {
        pause_menu.Render(_canvas);
        btn_resume.Render(_canvas);
        btn_exit.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {



        if (btn_resume.CheckButtonClick()) {
            System.out.println("Game Resumed");
            StateManager.Instance.RemoveOverlayState();
        }
        if (btn_exit.CheckButtonClick())
        {
            System.out.println("Exiting to Main Menu");
            StateManager.Instance.RemoveOverlayState();
            StateManager.Instance.ChangeState("Mainmenu");
            //Intent intent = new Intent();

        }
    }
}


