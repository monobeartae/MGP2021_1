package com.example.mgp2021_1;

import android.view.MotionEvent;

// Created by TanSiewLan2021

// Manages the touch events

public class TouchManager {

    public final static TouchManager Instance = new TouchManager();

    private TouchManager(){

    }

    public enum TouchState{
        NONE,
        DOWN,
        MOVE
    }

    private int posX, posY;
    private TouchState status = TouchState.NONE; //Set to default as NONE
    private TouchState prev_status = TouchState.NONE;

    public boolean HasTouch(){  // Check for a touch status on screen
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    public boolean IsDown(){
        return status == TouchState.DOWN;
    }

    public boolean IsClicked(){

        boolean isClick = (prev_status == TouchState.DOWN || prev_status == TouchState.MOVE)
                && status == TouchState.NONE;

        return isClick;
    }

    public void UpdateStatus()
    {
        prev_status = status;
    }

    public int GetPosX(){
        return posX;
    }

    public int GetPosY(){
        return posY;
    }

    public void Update(int _posX, int _posY, int _motionEventStatus){
        posX = _posX;
        posY = _posY;

        prev_status = status;

        switch (_motionEventStatus){
            case MotionEvent.ACTION_DOWN:
                status = TouchState.DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                status = TouchState.NONE;
                break;
        }
    }
}

