package com.example.mgp2021_1.Entities;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Camera;
import com.example.mgp2021_1.CatchTheTrash;
import com.example.mgp2021_1.Collidable;
import com.example.mgp2021_1.Collision;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;
import com.example.mgp2021_1.StateManager;
import com.example.mgp2021_1.TouchManager;

import java.util.Random;
import java.util.ResourceBundle;

public class AreaMarker implements EntityBase, Collidable {

    private boolean isDone = false;

    // Image BMP
    private Bitmap bmp = null;
    private int bmp_width, bmp_height;

    // Utility Vars
    int ScreenWidth, ScreenHeight;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    // AreaMarker Vars
    static float total_pollution=0;
    static int num_areas=0;

    private float xPos, yPos;
    public float pollution=30;
    private float pollution_rate = 0.1f;
    private boolean isCollided = false;

    // UI Entities
    CustomButton cleanButton = null;
    UIBackground areaDetailMenu = null;
    RenderTextEntity areaPollution = null;

    //check if anything to do with entity (use for pause)
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        // Create and Init BMP
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.redflag);

        //Find the surfaceview size or screensize
        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        bmp_width = ScreenWidth / 10;
        bmp_height = ScreenHeight / 7;

        bmp = Bitmap.createScaledBitmap(bmp, bmp_width, bmp_height, true);

        // Create and Init UI
        cleanButton = CustomButton.Create(R.drawable.clean_btn, 414, 102,
                ScreenWidth - 320, ScreenHeight - 300);
        cleanButton.Init(_view);

        areaDetailMenu = new UIBackground();
        areaDetailMenu.SetBMP(R.drawable.ui_frame1);
        areaDetailMenu.SetScale(600,700);
        areaDetailMenu.SetPos(1650,650);
        areaDetailMenu.Init(_view);

        areaPollution = new RenderTextEntity();
        areaPollution.SetFont("fonts/Contender.otf");
        areaPollution.Init(_view);
        areaPollution.SetPos(1450, 600);
        areaPollution.SetTextSize(70);
        areaPollution.SetColor(100,10,100);

        num_areas++;
    }

    @Override
    public void Update(float _dt) {

        if (pollution < 100)
        {
            pollution += pollution_rate * _dt;
        }
        areaPollution.SetText("Pollution: " + (int)pollution + "%");

        total_pollution += pollution;

        if (isCollided)
        {
            if (cleanButton.CheckButtonClick())
            {
                System.out.println("Clean Button Clicked");
                CatchTheTrash.area = this;
                StateManager.Instance.SetSecondaryState("Minigame");


            }
        }

        isCollided = false;
        cleanButton.SetHidden(true);
    }

    @Override
    public void Render(Canvas _canvas) {

        float screenPosX = (ScreenWidth / 2) + xPos - Camera.Instance.GetPosX() - (bmp_width / 2);
        float screenPosY = (ScreenHeight / 2) + yPos - Camera.Instance.GetPosY() - (bmp_height / 2);

        _canvas.drawBitmap(bmp, screenPosX, screenPosY, null); //1st image

        if (isCollided)
        {
            areaDetailMenu.Render(_canvas);
            areaPollution.Render(_canvas);
        }
    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.GAMEOBJECTS_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_AREAMARKER;
    }

    public static AreaMarker Create() {
        AreaMarker result = new AreaMarker();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_AREAMARKER);

        return result;
    }

    public void SetPos(float x, float y)
    {
        xPos = x;
        yPos = y;
    }

    @Override
    public float GetPosX()
    {
        return xPos;
    }

    @Override
    public float GetPosY()
    {
        return yPos;
    }

    @Override
    public float GetRadius()
    {
        return bmp_height;
    }

    @Override
    public String GetType() {
        return "AreaMarker";
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() == "Player") {  // Another entity
            isCollided = true;
            cleanButton.SetHidden(false);
        }
    }

    public static float GetAvgPollution()
    {
        System.out.println("Total Pollution: " + total_pollution);
        //System.out.println("Num Areas: " + num_areas);
        return total_pollution / num_areas;
    }

    public static void ResetTotalPollution()
    {
        total_pollution = 0;
    }
}
