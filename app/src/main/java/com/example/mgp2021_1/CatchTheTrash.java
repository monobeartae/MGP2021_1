package com.example.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp2021_1.Entities.AreaMarker;
import com.example.mgp2021_1.Entities.CustomButton;
import com.example.mgp2021_1.Entities.Joystick;
import com.example.mgp2021_1.Entities.Player;
import com.example.mgp2021_1.Entities.RenderBackground;
import com.example.mgp2021_1.Entities.RenderTextEntity;

public class CatchTheTrash implements StateBase
{
    private float timer = 0.0f;
    private RenderBackground bg = null;

    CustomButton pauseButton;

    Player player;

    @Override
    public String GetName() {
        return "Minigame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Create game scene bg
        bg = new RenderBackground();
        bg.SetBMP(R.drawable.gamescene);
        bg.SetBMPScale(1920, 1080);

        bg.Init(_view);

        Camera.Instance.SetPos(960,540);
        // Create Entities
        //RenderTextEntity.Create();


        // Create Buttons
        //pauseButton = CustomButton.Create(R.drawable.pausebutton, 100, 100, 55, 55);
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();

    }

    @Override
    public void Render(Canvas _canvas)
    {
        //EntityManager.Instance.Render(_canvas);
        //System.out.println("herro");
        bg.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {

        // All Entity Updates
        EntityManager.Instance.Update(_dt);

//        if(TouchManager.Instance.IsDown())
//        {
//            StateManager.Instance.RemoveSecondaryState();
//        }
        // Check for Pause Button Click
        //if (pauseButton.CheckButtonClick())
        //{
       //    System.out.println("Game Paused");
        //    StateManager.Instance.SetOverlayState("Pause");
       // }


    }
}
