package com.example.mgp2021_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HistoryMenu extends Activity implements View.OnClickListener, StateBase {

    //Define buttons
    private Button btn_back;
    private TextView text_bestTiming, text_History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.history);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Start Button

        text_bestTiming = (TextView) findViewById(R.id.text_best);
        if (GameSystem.Instance.GetIntFromSave("BestScore") == 0)
            text_bestTiming.setText("Best Record: NULL");
        else
            text_bestTiming.setText(String.format("Best Record: %d seconds", GameSystem.Instance.GetIntFromSave("BestScore")));
        text_History = (TextView) findViewById(R.id.text_history);

        String text = "LATEST CLEAR TIMINGS: \n";
        for (int i = 5; i > 0; i--)
        {
            if (GameSystem.Instance.GetIntFromSave("Score" + i) == 0)
                continue;
            text += "\n" + String.format("Time taken: %d", GameSystem.Instance.GetIntFromSave("Score" + i));
        }

        text_History.setText(text);



    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_back)
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
        return "PlayHistory";
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
