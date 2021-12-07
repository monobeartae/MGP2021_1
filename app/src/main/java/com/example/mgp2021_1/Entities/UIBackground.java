package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Camera;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.ResourceManager;

public class UIBackground implements EntityBase {

    private boolean isDone=false;
    private Bitmap bmp=null;
    private int bmp_width, bmp_height = 1;
    int ScreenWidth,ScreenHeight;
    private float xPos,yPos=0;
    private SurfaceView view=null;

    private int bg_id;
    //check if anything to do with entity (use for pause)
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
        bmp = ResourceManager.Instance.GetBitmap(bg_id);

        DisplayMetrics metrics=_view.getResources().getDisplayMetrics();
        ScreenHeight=metrics.heightPixels;
        ScreenWidth=metrics.widthPixels;

        bmp=Bitmap.createScaledBitmap(bmp,bmp_width,bmp_height,true);
    }


    @Override
    public void Update(float _dt) {
//        xPos-=_dt*500; //deals with the speed of moving the screen
//        if(xPos<=-ScreenWidth){
//            xPos=0;
//        }

    }

    @Override
    public void Render(Canvas _canvas) {

        _canvas.drawBitmap(bmp, xPos - (bmp_width / 2), yPos - (bmp_height / 2), null); //1st image

    }

    @Override
    public boolean IsInit() {
        return bmp!=null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.UIBG_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static UIBackground Create(){
        UIBackground result=new UIBackground();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public void SetBMP(int _id)
    {
        bg_id = _id;
    }

    public void SetScale(int x, int y)
    {
        bmp_width = x;
        bmp_height = y;
    }

    public void SetPos(int x, int y)
    {
        xPos = x;
        yPos = y;
    }
}
