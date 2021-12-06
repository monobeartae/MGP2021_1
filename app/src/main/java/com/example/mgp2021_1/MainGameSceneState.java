package com.example.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.Joystick;
import com.example.mgp2021_1.Entities.Player;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;
import com.example.mgp2021_1.Entities.Ship;
import com.example.mgp2021_1.Entities.Smurf;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;


    CustomButton pauseButton;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create();
        RenderTextEntity.Create();
        Joystick.Create();
        Player.Create();

        pauseButton = CustomButton.Create(R.drawable.pausebutton, 100, 100, 55, 55);


        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

    }

    @Override
    public void Update(float _dt) {

        EntityManager.Instance.Update(_dt);


        //if (TouchManager.Instance.IsClicked())
        if (pauseButton.CheckButtonClick())
        {

            System.out.println("Game Paused");
            StateManager.Instance.SetOverlayState("Pause");
        }
    }
}



