package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Camera;
import com.example.mgp2021_1.Collidable;
import com.example.mgp2021_1.Collision;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;
import com.example.mgp2021_1.TouchManager;

import java.util.Random;
import java.util.ResourceBundle;

public class Ship implements EntityBase {
    private boolean isDone = false;
    private Bitmap bmp = null;
    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, offset;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    private float playerSpeed = 100.0f;
    private int bmp_width, bmp_height;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);

        //Find the surfaceview size or screensize
        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        bmp_width = ScreenWidth / 7;
        bmp_height = ScreenHeight / 7;

        bmp = Bitmap.createScaledBitmap(bmp, bmp_width, bmp_height, true);

        // CODE EXAMPLE FOR RANDOM (leave here first for ref):
        //Random ranGen = new Random();
        //e.g. of things thatt can be done:
        // if wan spawn ship in random pos
        //xPos = ranGen.nextFloat() * _view.getWidth();
        //yPos = ranGen.nextFloat() * _view.getHeight();


        xPos = (5760 / 2) - (bmp_width / 2);
        yPos = (3240 / 2) - (bmp_height / 2);
    }

    @Override
    public void Update(float _dt) {
        //tfx.preRotate(20 * _dt,metrics.widthPixels / 10,metrics.heightPixels / 10);
        //tfx.postTranslate(10*_dt,10*_dt);

        xPos += Joystick.Instance.GetDirX() * _dt * playerSpeed;
        yPos += Joystick.Instance.GetDirY() * _dt * playerSpeed;

        Camera.Instance.SetPos(xPos, yPos);
        // SetIsDone(true); when end game?

    }

    @Override
    public void Render(Canvas _canvas) {

        float screenPosX = (ScreenWidth / 2) + xPos - Camera.Instance.GetPosX() - (bmp_width / 2);
        float screenPosY = (ScreenHeight / 2) + yPos - Camera.Instance.GetPosY() - (bmp_height / 2);

        _canvas.drawBitmap(bmp, screenPosX, screenPosY, null); //1st image

    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Ship Create() {
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}