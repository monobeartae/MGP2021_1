package com.example.mgp2021_1.Entities;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.AudioManager;
import com.example.mgp2021_1.CatchTheTrash;
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

public class Trash implements EntityBase, Collidable {
    private Bitmap bmp = null;
    private float bmp_width, bmp_height;

    private float xPos = 0;
    private float yPos = 0;

    private float speed = 0;

    public float spawnDelay = 0;

    private boolean isDone = false;
    private boolean isInit = false;

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

        bmp_width = ScreenWidth / 12;
        bmp_height = ScreenHeight / 9;

        Random ranGen = new Random();

        int trash_type = ranGen.nextInt(3);
        Bitmap image = null;
        switch (trash_type)
        {
            case 0:
                image = ResourceManager.Instance.GetBitmap(R.drawable.trash_1);
                break;
            case 1:
                image = ResourceManager.Instance.GetBitmap(R.drawable.trash_2);
                break;
            case 2:
                image = ResourceManager.Instance.GetBitmap(R.drawable.trash_3);
                break;
            default:
                break;
        }
        bmp = Bitmap.createScaledBitmap(image,
                (int)bmp_width, (int)bmp_height, true);


        xPos = ranGen.nextFloat() * (ScreenWidth - bmp_width) + (bmp_width * 0.5f);
        yPos = -1 * (bmp_height / 2);
        speed = ranGen.nextFloat() * 200.0f + 200.0f;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        if (spawnDelay > 0) {
            spawnDelay -= _dt;
            return;
        }


        yPos += speed * _dt;
        Constraint();
       // if (Collision.SphereToSphere(xPos, yPos, bmp_width * 0.5f, ))

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
        return LayerConstants.GAMEOBJECTS_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType() {
        return EntityBase.ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Trash Create() {
        Trash result = new Trash();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static Trash CreateTemp() {
        Trash result = new Trash();
        EntityManager.TempInstance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "TrashEntity";
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
        if (_other.GetType() == "NetEntity")
        {
            isDone = true;
            CatchTheTrash.trash_cleaned++;
            AudioManager.Instance.PlayAudio(R.raw.effect1);
            AudioManager.Instance.SetVolume(R.raw.effect1, 1.0f);
        }
    }

    private void Constraint()
    {
         if (yPos > ScreenHeight + (bmp_height / 2)) {
             SetIsDone(true);
             AudioManager.Instance.PlayAudio(R.raw.effect_break);
             AudioManager.Instance.SetVolume(R.raw.effect_break, 1.0f);
         }

    }
}