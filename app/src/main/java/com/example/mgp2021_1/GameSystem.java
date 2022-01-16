package com.example.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;


    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {



        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new PauseState());
        StateManager.Instance.AddState(new HelpMenu());
        StateManager.Instance.AddState(new OptionsMenu());
        StateManager.Instance.AddState(new CatchTheTrash());
        StateManager.Instance.AddState(new SweepTheTrash());


        // Get our shared preferences (Save File)
        //sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);

    }

    public void SaveEditBegin()
    {
        if (editor != null)
            return;

        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        if (editor == null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;

        editor.putInt(_key, _value);
        System.out.println("Key Value saved in Shared Preferences");
    }
    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 0);
    }


    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

}
