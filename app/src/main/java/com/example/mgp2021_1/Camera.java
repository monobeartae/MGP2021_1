package com.example.mgp2021_1;

public class Camera {
    public final static Camera Instance = new Camera();

    private float xPos, yPos;


    public void Init()
    {
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
    }

    void Update(float _dt)
    {
        // later
    }
}
