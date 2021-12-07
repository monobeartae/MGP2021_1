package com.example.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp2021_1.Entities.AreaMarker;
import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.Joystick;
import com.example.mgp2021_1.Entities.Player;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;
import com.example.mgp2021_1.Entities.Ship;
import com.example.mgp2021_1.Entities.Smurf;
import com.example.mgp2021_1.Entities.UIBackground;

import java.util.Vector;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {

    private float timer = 0.0f;

    // FPS
    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    // UI Entities
    CustomButton pauseButton = null;
    RenderTextEntity FPS = null;


    // GameObject Entities
    Player player = null;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Create and Init game scene bg
        RenderBackground bg = RenderBackground.Create();
        bg.SetBMP(R.drawable.testmap);
        bg.SetBMPScale(5760, 3240);

        // Create and Init UI Entities
        FPS = RenderTextEntity.Create();
        FPS.SetPos(30, 80);
        FPS.SetColor(0,0,0);

        Joystick.Create();

        // Create and init Game related entities
        player = Player.Create();

        float areaPos[] = {
                1000, 1000,
                50, 50,
                500, 700,
                3500, 1240
        };

        for (int i = 0; i < areaPos.length; i += 2)
        {
            AreaMarker temp = AreaMarker.Create();
            temp.SetPos(areaPos[i], areaPos[i + 1]);
        }

        // Create Buttons
        pauseButton = CustomButton.Create(R.drawable.pausebutton, 100, 100, 55, 55);
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

        // FPS
        frameCount++;
        long currentTime = System.currentTimeMillis();
        lastTime = currentTime;
        if(currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }
        FPS.SetText("FPS:" + fps);

        // All Entity Updates
        EntityManager.Instance.Update(_dt);

        // Check for Pause Button Click
        if (pauseButton.CheckButtonClick())
        {
            System.out.println("Game Paused");
            StateManager.Instance.SetOverlayState("Pause");
        }
    }
}



