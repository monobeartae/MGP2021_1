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

public class CustomButton implements EntityBase {

    private boolean isDone = false;

    private int bmp_id;
    private int posX, posY;
    private int scaleX, scaleY;

    private Bitmap bmp = null;


    private boolean hideButton = false;


    public CustomButton()
    {

    }

    public CustomButton(int _id, int scalex, int scaley,
                        int _posx, int _posy) {

        scaleX = scalex;
        scaleY = scaley;
        posX = _posx;
        posY = _posy;

        bmp = ResourceManager.Instance.GetBitmap(_id);
        bmp=Bitmap.createScaledBitmap(bmp,scaleX,
                scaleY, true);
    }

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
        bmp = ResourceManager.Instance.GetBitmap(bmp_id);
        bmp=Bitmap.createScaledBitmap(bmp,scaleX,
                scaleY, true);
    }

    @Override
    public void Update(float _dt) {

    }

    public boolean CheckButtonClick() {
        if (hideButton)
            return false;

        if (TouchManager.Instance.IsClicked()) {
            int touchX = TouchManager.Instance.GetPosX();
            int touchY = TouchManager.Instance.GetPosY();
            if (touchX <= posX + scaleX * 0.5 && touchX > posX - scaleX * 0.5
            && touchY <= posY + scaleY * 0.5 && touchY > posY - scaleY * 0.5)
            {
                return true;
            }
        }
        return false;

    }

    @Override
    public void Render(Canvas _canvas) {
        if (hideButton)
            return;

        _canvas.drawBitmap(bmp, posX - (scaleX / 2), posY - (scaleY / 2), null);
    }

    @Override
    public boolean IsInit() {
        return bmp!=null;
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
        return ENTITY_TYPE.ENT_UI;
    }

    public static CustomButton Create(int _id, int scalex, int scaley,
                                      int _posx, int _posy){
        CustomButton result=new CustomButton();
        result.bmp_id = _id;
        result.scaleX = scalex;
        result.scaleY = scaley;
        result.posX = _posx;
        result.posY = _posy;

        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_UI);
        return result;
    }

    public static CustomButton CreateTemp(int _id, int scalex, int scaley,
                                      int _posx, int _posy){
        CustomButton result=new CustomButton();
        result.bmp_id = _id;
        result.scaleX = scalex;
        result.scaleY = scaley;
        result.posX = _posx;
        result.posY = _posy;

        EntityManager.TempInstance.AddEntity(result,ENTITY_TYPE.ENT_UI);
        return result;
    }

    public void SetHidden(boolean show)
    {
        hideButton = show;
    }
}
