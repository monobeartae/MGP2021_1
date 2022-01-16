package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Collidable;
import com.example.mgp2021_1.Collision;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;
import com.example.mgp2021_1.Sprite;
import com.example.mgp2021_1.StateManager;
import com.example.mgp2021_1.TouchManager;

import java.util.Random;

public class Net implements EntityBase, Collidable {
    private Bitmap bmp = null;
    private float bmp_width, bmp_height;

    private float xPos = 0;
    private float yPos = 0;

    private boolean isDone = false;
    private boolean isInit = false;
    private boolean isHeld = false;

    int ScreenWidth, ScreenHeight;


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

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        bmp_width = ScreenWidth / 8;
        bmp_height = ScreenHeight / 5;

        bmp = Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.fishing_net),
                (int)bmp_width, (int)bmp_height, true);

//        Random ranGen = new Random();
        xPos = ScreenWidth / 2;
        yPos = ScreenHeight * 0.75f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {


        float imgRadius = bmp.getHeight() * 0.5f;
        if (isHeld)
        {
            // System.out.println("Net::Update::Collision with touch pos and net");
            xPos = TouchManager.Instance.GetPosX();
           // yPos = TouchManager.Instance.GetPosY();
            Constraint();

            if (!TouchManager.Instance.IsDown() && !TouchManager.Instance.HasTouch())
            {
                isHeld = false;
            }
        }
        else if (TouchManager.Instance.HasTouch()
        && Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f,
                xPos, yPos, imgRadius))
        {
            isHeld = true;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, xPos - (bmp_width / 2), yPos - (bmp_height / 2),null);
    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PLAYER_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType() {
        return EntityBase.ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Net Create() {
        Net result = new Net();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static Net CreateTemp() {
        Net result = new Net();
        EntityManager.TempInstance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "NetEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    private void Constraint()
    {
        if (xPos - (bmp_width / 2) < 0)
            xPos = bmp_width / 2;
        else if (xPos + (bmp_width / 2) > ScreenWidth)
            xPos = ScreenWidth - (bmp_width / 2);

        if (yPos - (bmp_height / 2) < ScreenHeight * 0.6f)
            yPos = (ScreenHeight * 0.6f) + (bmp_height / 2);
        else if (yPos + (bmp_height / 2) > ScreenHeight)
            yPos = ScreenHeight - (bmp_height / 2);

    }
}