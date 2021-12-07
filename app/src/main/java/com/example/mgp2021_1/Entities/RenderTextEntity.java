package com.example.mgp2021_1.Entities;

// Created by TanSiewLan2020

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import com.example.mgp2021_1.EntityManager;
import com.example.mgp2021_1.LayerConstants;

public class RenderTextEntity implements EntityBase {

        // Paint object
        Paint paint = new Paint();
        private int red = 0, green = 0, blue = 0;
        private float posX, posY = 0;
        private int text_size = 70;

        private boolean isDone = false;
        private boolean isInit = false;

        Typeface myfont;

        private String text;

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

            // Week 8 Use my own fonts
            myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
           // myfont = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
            isInit = true;

        }

        @Override
        public void Update(float _dt) {

        }

        @Override
        public void Render(Canvas _canvas)
        {

            Paint paint = new Paint();
            paint.setARGB(255, red,green,blue);
            //paint.setStrokeWidth(200);
            paint.setTypeface(myfont);
            paint.setTextSize(text_size);
            _canvas.drawText(text, posX, posY, paint);


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
            return;
        }

        @Override
        public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_TEXT;}

        public static RenderTextEntity Create()
        {
            RenderTextEntity result = new RenderTextEntity();
            EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
            return result;
        }

        public void SetText(String _text)
        {
            text = _text;
        }

        public void SetColor(int r, int g, int b)
        {
            red = r;
            green = g;
            blue = b;
        }

        public void SetPos(int _x, int _y)
        {
            posX = _x;
            posY = _y;
        }

        public void SetTextSize(int _size)
        {
            text_size = _size;
        }

}

