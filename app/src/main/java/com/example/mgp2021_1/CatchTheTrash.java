package com.example.mgp2021_1;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Entities.AreaMarker;
import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.EntityBase;
import com.example.mgp2021_1.Entities.Joystick;
import com.example.mgp2021_1.Entities.Net;
import com.example.mgp2021_1.Entities.Player;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;
import com.example.mgp2021_1.Entities.Trash;

import java.util.LinkedList;

public class CatchTheTrash implements StateBase
{

    private float timer = 10.0f;
    private RenderBackground bg = null;
    private Net net = null;

    private int ScreenWidth, ScreenHeight;

    CustomButton pauseButton;
    RenderTextEntity timer_text = null;


    private LinkedList<Trash> trashList = new LinkedList<Trash>();

    @Override
    public String GetName() {
        return "Minigame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        // Create game scene bg
        bg = RenderBackground.CreateTemp();
        bg.SetBMP(R.drawable.ctt_bg);
        bg.SetBMPScale(ScreenWidth, ScreenHeight);

        Camera.Instance.SetPos(ScreenWidth / 2,ScreenHeight / 2);

        // Create Entities

        net = Net.CreateTemp();


        // Create UI
        // Create Buttons
        pauseButton = CustomButton.CreateTemp(R.drawable.pausebutton, 100, 100, 1920 - 70, 70);

        timer_text = RenderTextEntity.CreateTemp();
        timer_text.SetColor(255,255,255);
        timer_text.SetPos(ScreenWidth / 2, 70);
        timer_text.SetFont("fonts/EnsoBold.ttf");
        timer_text.SetText(" ");

    }

    @Override
    public void OnExit() {
        EntityManager.TempInstance.Clean();
    }

    @Override
    public void Render(Canvas _canvas)
    {

        EntityManager.TempInstance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {

        // All Entity Updates
        EntityManager.TempInstance.Update(_dt);

        Camera.Instance.SetPos(ScreenWidth / 2,ScreenHeight / 2);

        // Check for Pause Button Click
        if (pauseButton.CheckButtonClick())
        {
            System.out.println("Game Paused");
            StateManager.Instance.SetOverlayState("Pause");
        }

        timer -= _dt;
        timer_text.SetText("" + (int)timer);
        if (timer <= 0.0)
            StateManager.Instance.RemoveSecondaryState();

    }
}
