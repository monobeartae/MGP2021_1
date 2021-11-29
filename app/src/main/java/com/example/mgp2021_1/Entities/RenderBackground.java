package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;

public class RenderBackground implements EntityBase {
    private boolean isDone=false;
    private Bitmap bmp=null,scaledbmp=null;
    int ScreenWidth,ScreenHeight;
    private float xPos,yPos, offset;
    private SurfaceView view=null;
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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.gamescene);

        //Find the surfaceview size or screensize
        DisplayMetrics metrics=_view.getResources().getDisplayMetrics();
        ScreenHeight=metrics.heightPixels;
        ScreenWidth=metrics.widthPixels;
        scaledbmp=Bitmap.createScaledBitmap(bmp,ScreenWidth,ScreenHeight,true);

    }

    @Override
    public void Update(float _dt) {
        xPos-=_dt*500; //deals with the speed of moving the screen
        if(xPos<=-ScreenWidth){
            xPos=0;
        }



    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(scaledbmp,xPos,yPos,null); //1st image
        _canvas.drawBitmap(scaledbmp,xPos+ScreenWidth,yPos,null); //2nd image (to show its continuous)
//Matrix transform=new Matrix();
    }

    @Override
    public boolean IsInit() {
        return bmp!=null;
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

    public static RenderBackground Create(){
        RenderBackground result=new RenderBackground();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}