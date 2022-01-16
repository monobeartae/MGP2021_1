package com.example.mgp2021_1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.mgp2021_1.Entities.AreaMarker;
import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.EntityBase;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;
import com.example.mgp2021_1.Entities.Trash;

import java.util.Random;

public class SweepTheTrash implements StateBase, SensorEventListener {
    private float timer;
    private float minigame_duration = 10.0f;
    private RenderBackground bg = null;

    private int ScreenWidth, ScreenHeight;

    CustomButton pauseButton;
    RenderTextEntity timer_text = null;

    public static AreaMarker area = null;


    SensorManager sensor = null;
    private float[] sensor_values = {0,0,0};

    @Override
    public String GetName() {
        return "STTMiniGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {

        // Set Up Accelerator
        sensor = (SensorManager)_view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor.registerListener(this, sensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);

        // Variable Inits
        timer = minigame_duration;

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        // Create game scene bg
        bg = RenderBackground.CreateTemp();
        bg.SetBMP(R.drawable.ctt_bg);
        bg.SetBMPScale(ScreenWidth, ScreenHeight);

        Camera.Instance.SetPos(ScreenWidth / 2,ScreenHeight / 2);

        // Create Entities


        // Create UI
        // Create Buttons
        pauseButton = CustomButton.CreateTemp(R.drawable.pausebutton, 100, 100, 1920 - 70, 70);

        timer_text = RenderTextEntity.CreateTemp();
        timer_text.SetColor(255,255,255);
        timer_text.SetPos(ScreenWidth / 2, 70);
        timer_text.SetFont("fonts/EnsoBold.ttf");
        timer_text.SetText(" ");



        // Game Related Vars
        // Create Trash Entities
//        trash_cleaned = 0;
//        trash_num = (int)area.pollution / 5;
//        for (int i = 0; i < trash_num; i++)
//        {
//            Trash trash = Trash.CreateTemp();
//            trash.spawnDelay = new Random().nextFloat() * (minigame_duration - 3); //3 is so it wont spawn just before game ends
//            System.out.println("Trash Spawned. Trash Spawn Delay set to: " + trash.spawnDelay);
//        }

        EntityManager.TempInstance.InitEntities(_view);

    }

    @Override
    public void OnExit() {

        EntityManager.TempInstance.Clean();
        Camera.Instance.SetPos(MainGameSceneState.player.GetPosX(), MainGameSceneState.player.GetPosY());
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
        EntityManager.Instance.Update(EntityBase.ENTITY_TYPE.ENT_AREAMARKER, _dt);

        //Camera.Instance.SetPos(ScreenWidth / 2,ScreenHeight / 2);

        // Check for Pause Button Click
        if (pauseButton.CheckButtonClick())
        {
            System.out.println("Game Paused");
            StateManager.Instance.SetOverlayState("Pause");
        }

        timer -= _dt;
        timer_text.SetText("" + (int)timer);
        if (timer <= 0.0)
            EndMiniGame();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensor_values = event.values;
        System.out.println("SENSOR VALUES:" + sensor_values[0] + ", " + sensor_values[1] + ", " + sensor_values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void EndMiniGame()
    {
        StateManager.Instance.RemoveSecondaryState();
    }

}
