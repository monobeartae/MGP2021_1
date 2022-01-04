package com.example.mgp2021_1;

// Created by TanSiewLan2021

// StateManager to deal with which state is current or next.

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.HashMap;

public class StateManager {
    // Singleton Instance
    public static final StateManager Instance = new StateManager();

    // Container to store all our states!
    private HashMap<String, StateBase> stateMap = new HashMap<String, StateBase>();
    private StateBase currState = null;
    private StateBase nextState = null;
    private StateBase overlayState = null; // pause // curr doesnt update, both render
    private StateBase secondaryState = null; // minigame // curr doesnt update or render

    private SurfaceView view = null;

    // This is the protected constructor for singleton
    private StateManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    void AddState(StateBase _newState)
    {
        // Add the state into the state map
        stateMap.put(_newState.GetName(), _newState);
    }

    public void ChangeState(String _nextState)
    {
        // Try to assign the next state
        nextState = stateMap.get(_nextState);

        // If no next state, we assign back to current state
        if (nextState == null)
            nextState = currState;

        // Extra to add if possible : throw some warning if next state function fails
    }

    public void SetOverlayState(String _overlayState)
    {
        if (overlayState == null) {
            overlayState = stateMap.get(_overlayState);
            overlayState.OnEnter(view);
        }
    }

    public void SetSecondaryState(String _secondaryState)
    {
        if (secondaryState == null) {
            secondaryState = stateMap.get(_secondaryState);
            secondaryState.OnEnter(view);

        }
    }

    void RemoveOverlayState()
    {
        if (overlayState != null) {
            overlayState.OnExit();
            overlayState = null;
        }
    }

    public void RemoveSecondaryState()
    {
        if (secondaryState != null) {
            secondaryState.OnExit();
            secondaryState = null;
        }
    }

    public void Update(float _dt)
    {
        if (nextState != currState)
        {
            // We need to change state
            currState.OnExit();
            nextState.OnEnter(view);
            currState = nextState;
        }

        if (currState == null)
            return;

        if (overlayState != null)
            overlayState.Update(_dt);
        else if (secondaryState != null)
            secondaryState.Update(_dt);
        else
            currState.Update(_dt);
    }

    void Render(Canvas _canvas)
    {


        if (secondaryState != null)
            secondaryState.Render(_canvas);
        else
            currState.Render(_canvas);

        if (overlayState != null)
            overlayState.Render(_canvas);

    }

    public String GetCurrentState()
    {
        if (currState == null)
            return "INVALID";

        return currState.GetName();
    }

    void Start(String _newCurrent)
    {
        // Make sure only can call once at the start
        if (currState != null || nextState != null)
            return;

        currState = stateMap.get(_newCurrent);
        if (currState != null)
        {
            currState.OnEnter(view);
            nextState = currState;
        }
    }

    public void Clean(){
        stateMap.clear();
    }
}
