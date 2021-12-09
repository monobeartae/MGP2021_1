package com.example.mgp2021_1;

import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class Camera {
    public final static Camera Instance = new Camera();

    int ScreenWidth, ScreenHeight;

    private float xPos, yPos;
    private float targetX, targetY;

    private float cam_speed=2.0f;


    public void Init(SurfaceView _view)
    {

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        xPos = yPos = 0;
    }

    public float GetPosX()
    {
        return xPos;
    }

    public float GetPosY()
    {
        return yPos;
    }

    public void SetPos(float _x, float _y)
    {
        xPos = _x;
        yPos = _y;

        targetX = xPos;
        targetY = yPos;
    }

    public void SetTarget(float _x, float _y)
    {
        targetX = _x;
        targetY = _y;
    }

    void Update(float _dt)
    {
        xPos += (targetX - xPos) * cam_speed *  _dt;
        yPos += (targetY - yPos) * cam_speed *  _dt;
        Constraint();
    }

    private void Constraint()
    {

        if (xPos - (ScreenWidth / 2) < 0)
            xPos = ScreenWidth / 2;
        else if (xPos + (ScreenWidth / 2) > 5760)
            xPos = 5760 - (ScreenWidth / 2);

        if (yPos - (ScreenHeight / 2) < 0)
            yPos = ScreenHeight / 2;
        else if (yPos + (ScreenHeight / 2) > 3240)
            yPos = 3240 - (ScreenHeight / 2);
    }
}
