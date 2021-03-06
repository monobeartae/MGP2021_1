package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp2021_1.Camera;
import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.GamePage;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;
import com.example.mgp2021_1.ResourceManager;

public class RenderBackground implements EntityBase {
    private boolean isDone=false;
    private Bitmap bmp=null;
    private int bmp_width, bmp_height = 1;
    int ScreenWidth,ScreenHeight;
    private float xPos,yPos, offset;
    private SurfaceView view=null;

    GamePage aaa;

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
       // bmp = ResourceManager.Instance.GetBitmap(bg_id);

        DisplayMetrics metrics=_view.getResources().getDisplayMetrics();
        ScreenHeight=metrics.heightPixels;
        ScreenWidth=metrics.widthPixels;

       // bmp_width = 5760;
        //bmp_height = 3240;
        bmp=Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(bg_id),bmp_width,bmp_height,true);


        xPos = bmp_width / 2;
        yPos = bmp_height / 2;
    }


    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {

        float screenPosX = (ScreenWidth / 2) + xPos - Camera.Instance.GetPosX() - (bmp_width / 2);
        float screenPosY = (ScreenHeight / 2) + yPos - Camera.Instance.GetPosY() - (bmp_height / 2);


        _canvas.drawBitmap(bmp, screenPosX, screenPosY, null); //1st image
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

    public static RenderBackground CreateTemp(){
        RenderBackground result=new RenderBackground();
        EntityManager.TempInstance.AddEntity(result,ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public void SetBMP(int _id)
    {
        bg_id = _id;
    }

    public void SetBMPScale(int x, int y)
    {
        bmp_width = x;
        bmp_height = y;
    }
}