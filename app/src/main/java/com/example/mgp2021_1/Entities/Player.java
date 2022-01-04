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
import com.example.mgp2021_1.Sprite;
import com.example.mgp2021_1.TouchManager;

import java.util.Random;
import java.util.ResourceBundle;

public class Player implements EntityBase, Collidable {
    private boolean isDone = false;

    //private Bitmap bmp = null;
    private int bmp_width, bmp_height;
    private Sprite player_sprite = null;
    private Sprite player_up = null;
    private Sprite player_down = null;
    private Sprite player_left = null;
    private Sprite player_right = null;

    int ScreenWidth, ScreenHeight;
    private float xPos, yPos, offset;

    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    private float playerSpeed = 300.0f;


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


        //Find the surfaceview size or screensize
        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels;
        ScreenWidth = metrics.widthPixels;

        bmp_width = ScreenWidth / 12;
        bmp_height = ScreenHeight / 6;

        Bitmap up_bmp = ResourceManager.Instance.GetBitmap(R.drawable.player_up);
        Bitmap down_bmp = ResourceManager.Instance.GetBitmap(R.drawable.player_down);
        Bitmap left_bmp = ResourceManager.Instance.GetBitmap(R.drawable.player_left);
        Bitmap right_bmp = ResourceManager.Instance.GetBitmap(R.drawable.player_right);
        up_bmp = Bitmap.createScaledBitmap(up_bmp, bmp_width * 4, bmp_height, true);
        down_bmp = Bitmap.createScaledBitmap(down_bmp, bmp_width * 4, bmp_height, true);
        left_bmp = Bitmap.createScaledBitmap(left_bmp, bmp_width * 4, bmp_height, true);
        right_bmp = Bitmap.createScaledBitmap(right_bmp, bmp_width * 4, bmp_height, true);

        player_up = new Sprite(up_bmp,1,4, 4 );
        player_down = new Sprite(down_bmp,1,4, 4 );
        player_left = new Sprite(left_bmp,1,4, 4 );
        player_right = new Sprite(right_bmp,1,4, 4 );

        player_sprite =  player_down;

        xPos = (5760 / 2) - (bmp_width / 2);
        yPos = (3240 / 2) - (bmp_height / 2);

        Camera.Instance.SetPos(xPos, yPos);
    }

    @Override
    public void Update(float _dt) {

        float dirX = Joystick.Instance.GetDirX();
        float dirY = Joystick.Instance.GetDirY();

        xPos += dirX * _dt * playerSpeed;
        yPos += dirY * _dt * playerSpeed;

        Constraint();

        Camera.Instance.SetTarget(xPos, yPos);

        if (dirX * dirX > dirY * dirY)
        {
            if (dirX > 0)
                player_sprite = player_right;
            else
                player_sprite = player_left;
        }
        else
        {
            if (dirY >= 0)
                player_sprite = player_down;
            else
                player_sprite = player_up;
        }

        if (Joystick.Instance.GetDisplacement() != 0)
            player_sprite.Update(_dt);

    }

    @Override
    public void Render(Canvas _canvas) {

        float screenPosX = (ScreenWidth / 2) + xPos - Camera.Instance.GetPosX();// - (bmp_width / 2);
        float screenPosY = (ScreenHeight / 2) + yPos - Camera.Instance.GetPosY();// - (bmp_height / 2);

        //_canvas.drawBitmap(bmp, screenPosX, screenPosY, null); //1st image
        player_sprite.Render(_canvas, (int)screenPosX, (int)screenPosY);

    }

    @Override
    public boolean IsInit() {
        return player_sprite != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PLAYER_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PLAYER;
    }

    public static Player Create() {
        Player result = new Player();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
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
        return bmp_width;
    }

    @Override
    public String GetType() {
        return "Player";
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    private void Constraint()
    {
        if (xPos - (bmp_width / 2) < 0)
            xPos = bmp_width / 2;
        else if (xPos + (bmp_width / 2) > 5760)
            xPos = 5760 - (bmp_width / 2);

        if (yPos - (bmp_height / 2) < 0)
            yPos = bmp_height / 2;
        else if (yPos + (bmp_height / 2) > 3240)
            yPos = 3240 - (bmp_height / 2);
    }

}
