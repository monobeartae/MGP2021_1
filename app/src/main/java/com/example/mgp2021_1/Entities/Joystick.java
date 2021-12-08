package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Collision;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;
import com.example.mgp2021_1.TouchManager;

public class Joystick implements EntityBase {

    public static Joystick Instance;

    private boolean isDone=false;

    private float originX, originY;
    private float posX, posY;

    private int inner_radius=50;
    private int outer_radius=100;
    private float max_d=75;

    private boolean isHeld=false;


    private Bitmap bg_bmp=null,joystick_bmp=null;


    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone=_isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        //bg_bmp = ResourceManager.Instance.GetBitmap(R.drawable.joystick_bg);
        //joystick_bmp = ResourceManager.Instance.GetBitmap(R.drawable.joystick);

        DisplayMetrics metrics=_view.getResources().getDisplayMetrics();
        bg_bmp=Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.joystick_bg),outer_radius * 2,
                outer_radius * 2, true);
        joystick_bmp=Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.joystick),inner_radius * 2,
                inner_radius * 2, true);

        posX=originX=outer_radius + 70;
        posY=originY=metrics.heightPixels - outer_radius - 70;


    }

    @Override
    public void Update(float _dt) {

        if (!isHeld)
        {
            if (TouchManager.Instance.IsDown() &&
                    Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f,
                            posX, posY, inner_radius))
            {
                isHeld = true;
            }
        }
        else
        {
            if (!TouchManager.Instance.HasTouch())
            {
                isHeld = false;
                posX = originX;
                posY = originY;
                return;
            }

            int touchX = TouchManager.Instance.GetPosX();
            int touchY = TouchManager.Instance.GetPosY();
            posX = touchX;
            posY = touchY;
            float displacementX = touchX - originX;
            float displacementY = touchY - originY;
            float dis_sqred = displacementX * displacementX + displacementY * displacementY;

            if (dis_sqred > max_d * max_d)
            {
               posX = originX + (float)(displacementX / Math.sqrt(dis_sqred)) * max_d;
               posY = originY + (float)(displacementY / Math.sqrt(dis_sqred)) * max_d;
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bg_bmp, originX - outer_radius, originY - outer_radius, null);
        _canvas.drawBitmap(joystick_bmp, posX - inner_radius, posY - inner_radius, null);
    }

    @Override
    public boolean IsInit() {
        return bg_bmp!=null&&joystick_bmp!=null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.UI_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static Joystick Create(){
        Joystick result=new Joystick();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_DEFAULT);
        Instance = result;
        return result;
    }

    public float GetDirX()
    {

        if (!isHeld)
            return 0.0f;

        float length = GetDisplacement();
        if (length == 0.0f)
            return 0.0f;

        float disX = posX - originX;
        return (float)(disX / length);
    }

    public float GetDirY()
    {
        if (!isHeld)
            return 0.0f;

        float length = GetDisplacement();
        if (length == 0.0f)
            return 0.0f;

        float disY = posY - originY;
        return (float)(disY / length);
    }

    public float GetDisplacement()
    {
        float disX = posX - originX;
        float disY = posY - originY;
        float disSqred = disX * disX + disY * disY;
        return (float)Math.sqrt(disX * disX + disY * disY);
    }

}
