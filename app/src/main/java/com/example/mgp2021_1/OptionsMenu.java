package com.example.mgp2021_1;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;



public class OptionsMenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_back;
    private Button btn_Helpmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.optionmenu);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_Helpmenu = (Button)findViewById(R.id.btn_helpmenu);
        btn_Helpmenu.setOnClickListener(this); //Set Listener to this button --> Back Button


        StateManager.Instance.AddState(new OptionsMenu());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_Helpmenu)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, HelpMenu.class);
            StateManager.Instance.ChangeState("HelpMenu"); // Default is like a loading page

        }

        else if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
        }

        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Optionsmenu";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
