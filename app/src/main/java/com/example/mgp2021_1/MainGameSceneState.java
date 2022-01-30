package com.example.mgp2021_1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.view.SurfaceView;

import android.util.DisplayMetrics;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;

import com.example.mgp2021_1.Entities.AreaMarker;
import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.Joystick;
import com.example.mgp2021_1.Entities.Player;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;
import com.example.mgp2021_1.Entities.UIBackground;

import java.util.Vector;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {

    public static float timer = 0.0f;

    // MEtrics
    DisplayMetrics metrics;
    int ScreenWidth, ScreenHeight;

    // FPS
    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    // UI Entities
    CustomButton pauseButton = null;
    RenderTextEntity FPS = null;

    //TEMP
   // RenderTextEntity pos_text = null;

    // GameObject Entities
    public static Player player = null;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        timer = 0;

        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        Camera.Instance.Init(_view);

        // Create and Init game scene bg
        RenderBackground bg = RenderBackground.Create();
        bg.SetBMP(R.drawable.map_2);
        bg.SetBMPScale(5760, 3240);

        // Create and Init UI Entities
        FPS = RenderTextEntity.Create();
        FPS.SetFont("fonts/Steelworks.ttf");
        FPS.SetPos(30, 80);
        FPS.SetColor(0,0,0);

//        pos_text = RenderTextEntity.Create();
//        pos_text.SetFont("fonts/EnsoBold.ttf");
//        pos_text.SetPos(0, 1040);
//        pos_text.SetColor(0,0,0);

        Joystick.Create();

        // Create and init Game related entities
        player = Player.Create();

        AreaMarker.total_pollution=0;
        AreaMarker.num_areas=0;
        float areaPos[] = {
                1710, 970,
                2740, 750,
                2250, 1700,
                5070, 2570,
                4410, 1680,
                2980, 2330
        };

        for (int i = 0; i < areaPos.length; i += 2)
        {
            AreaMarker temp = AreaMarker.Create();
            temp.SetPos(areaPos[i], areaPos[i + 1]);
        }

        // Create Buttons
        pauseButton = CustomButton.Create(R.drawable.pausebutton, 100, 100,
                (int)(ScreenWidth * 0.96), (int)(ScreenHeight * 0.06));

        AudioManager.Instance.PlayAudio(R.raw.monkeys);
        AudioManager.Instance.SetVolume(R.raw.monkeys, 1.0f);
        AudioManager.Instance.LoopAudio(R.raw.monkeys);
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

        // Pollution Bar
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setARGB(255, 255, 255, 255);
        paint.setStyle(Paint.Style.STROKE);
        _canvas.drawRect((ScreenWidth / 2) - (ScreenWidth * 0.255f), 10,
                (ScreenWidth / 2) + (ScreenWidth * 0.265f),70, paint);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL);
        _canvas.drawRect((ScreenWidth / 2) - (ScreenWidth * 0.255f), 15,
                (ScreenWidth / 2) - (ScreenWidth * 0.26f) + (AreaMarker.GetAvgPollution() / 100) * 1000.0f,65, paint);

    }

    @Override
    public void Update(float _dt) {

        timer += _dt;

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
        FPS.SetText("fps:" + fps);

       // pos_text.SetText("Pos: " + player.GetPosX() + ", " + player.GetPosY());

        Camera.Instance.Update(_dt);

        AreaMarker.ResetTotalPollution();
        // All Entity Updates
        EntityManager.Instance.Update(_dt);

        // Check for Pause Button Click
        if (pauseButton.CheckButtonClick())
        {
            System.out.println("Game Paused");
            StateManager.Instance.SetOverlayState("Pause");
        }

        // Check for End Game Condition
        if (AreaMarker.GetAvgPollution() < 20 && !GameWinDialog.isShown)
        {
            // Cleared
            RecordTiming();
            GameWinDialog winDialog = new GameWinDialog();
            winDialog.show(GamePage.Instance.getSupportFragmentManager(), "GameWin");
        }
        else if (AreaMarker.GetAvgPollution() > 60 && !GameLostDialog.isShown)
        {
            // Lost
            GameLostDialog loseDialog = new GameLostDialog();
            loseDialog.show(GamePage.Instance.getSupportFragmentManager(), "GameLost");
        }

    }

    public static void RecordTiming()
    {
        //TEST
        GameSystem.Instance.SaveEditBegin();

        if (timer < GameSystem.Instance.GetIntFromSave("BestScore") || GameSystem.Instance.GetIntFromSave("BestScore") == 0)
            GameSystem.Instance.SetIntInSave("BestScore", (int)timer);

        for (int i = 1; i < 6; i++)
        {
            if (GameSystem.Instance.GetIntFromSave("Score" + i) == 0)
            {
                GameSystem.Instance.SetIntInSave("Score" + i, (int)timer);
                GameSystem.Instance.SaveEditEnd();
                return;
            }
        }

        for (int i = 1; i < 5; i++) {
            GameSystem.Instance.SetIntInSave("Score" + i, GameSystem.Instance.GetIntFromSave("Score" + (i + 1)));
        }
        GameSystem.Instance.SetIntInSave("Score5", (int)timer);

        GameSystem.Instance.SaveEditEnd();
    }

}



