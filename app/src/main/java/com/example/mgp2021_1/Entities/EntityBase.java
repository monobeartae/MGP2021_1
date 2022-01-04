package com.example.mgp2021_1.Entities;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public interface EntityBase
{
 	 //used for entities such as background
    enum ENTITY_TYPE{
        ENT_PLAYER,
        ENT_AREAMARKER,
        ENT_UI,
        ENT_TEXT,
        //ENT_NEXT,

        ENT_DEFAULT,
    }

    boolean IsDone();
    void SetIsDone(boolean _isDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);

	ENTITY_TYPE GetEntityType();
}
