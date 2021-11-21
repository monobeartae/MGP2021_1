package com.example.mgp2021_1;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;

public class PauseState implements StateBase {

    private Button btn_resume;

    public String GetName() {
        return "Pause";
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void Update(float _dt) {
        if (TouchManager.Instance.IsClicked()) {
            System.out.println("Game Resumed");
            StateManager.Instance.RemoveOverlayState();
        }
    }
}


