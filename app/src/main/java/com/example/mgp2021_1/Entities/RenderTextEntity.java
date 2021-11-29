package com.example.mgp2021_1.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;
import com.example.mgp2021_1.R;

public class RenderTextEntity implements EntityBase {

    private boolean isDone = false;
    //private Bitmap bmp = null;
    Paint paint = new Paint();
    private int red = 0, green = 0, blue = 0; // 0 - 255

    Typeface myfont;

    // Use for loading FPS so need da more parameters!
    int frameCount; // Framecount
    long lastTime = 0; // Time
    long lastFPSTime = 0; // last frame time
    float fps; // to store fps

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
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(),"fonts/Gemcut.otf");
    }

    @Override
    public void Update(float _dt) {
        // Load a text FPS: the real FPS
        // get actual fps

        frameCount++;

        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if (currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        // we using PAINT which is part of graphic library in android
        paint.setARGB(255, 0,0,0);
        paint.setStrokeWidth(200); // thickness of font
        paint.setTypeface(myfont);
        paint.setTextSize(70);
        _canvas.drawText("FPS: " + fps, 30, 80, paint);
    }

    @Override
    public boolean IsInit() {
        return true;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderTextEntity Create(){
        RenderTextEntity result=new RenderTextEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }
}
