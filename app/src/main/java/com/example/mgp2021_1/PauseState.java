package com.example.mgp2021_1;

import android.content.Intent;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.mgp2021_1.Entities.CustomButton;

public class PauseState implements StateBase {

    private CustomButton btn_resume, btn_exit = null;


    public String GetName() {
        return "Pause";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        int ScreenHeight = metrics.heightPixels;
        int ScreenWidth = metrics.widthPixels;

        btn_resume = new CustomButton(R.drawable.resumebutton, 316, 139,
                ScreenWidth / 2, ScreenHeight - 600);
        btn_exit = new CustomButton(R.drawable.exitbutton, 316, 139,
                ScreenWidth / 2, ScreenHeight - 400);
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {
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


