package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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

public class Smurf implements EntityBase, Collidable {
    private Bitmap bmp = null;

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;
    private boolean hasTouched = false;

    private float lifetime;

    private Sprite spritesmurf = null;
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

        // New method using our own resource manager : Returns pre-loaded one if exists
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite);

        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );

        Random ranGen = new Random();
        xPos = (5760 / 2);
        yPos = (3240 / 2);

        lifetime = 30.0f;
        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        // Do nothing if it is not in the main game state
        if (StateManager.Instance.GetCurrentState() != "MainGame")
            return;

        spritesmurf.Update(_dt);

        if(TouchManager.Instance.HasTouch()) {   // Previous and it is for just a touch - useful for collision with a image (example button)

            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)) {
                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }
        }


    }

    @Override
    public void Render(Canvas _canvas) {

//        Matrix transform = new Matrix();
//        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
//
//        transform.postTranslate(xPos, yPos);
//        _canvas.drawBitmap(bmp, transform, null);

        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);  // Location can be changed!

    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.STAR_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){ return EntityBase.ENTITY_TYPE.ENT_DEFAULT;}

    public static Smurf Create()
    {
        Smurf result = new Smurf();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "StarEntity";
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
        return bmp.getWidth();
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "SmurfEntity") {  // Another entity
            SetIsDone(true);
        }
    }
}
