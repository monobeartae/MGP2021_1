package com.example.mgp2021_1;

// Tan Siew Lan

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager {
    public final static AudioManager Instance = new AudioManager();

    private Resources res = null;
    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        res = _view.getResources();
    }

    public void PlayAudio(int _id)
    {
        if (audioMap.containsKey(_id))
        {

            audioMap.get(_id).seekTo(0);
            audioMap.get(_id).start();
            return;
        }



        // Load the audio
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id, newAudio);
        newAudio.start();
    }

    public void PauseAudio(int _id)
    {
        if (!audioMap.containsKey(_id))
            return;

        audioMap.get(_id).pause();
    }
    public void ResumeAudio(int _id)
    {
        if (!audioMap.containsKey(_id))
            return;

        audioMap.get(_id).start();
    }

    public void SetVolume(int _id, float volume)
    {
        if (!audioMap.containsKey(_id))
            return;

        audioMap.get(_id).setVolume(volume, volume);
    }

    public void LoopAudio(int _id)
    {
        if (!audioMap.containsKey(_id))
            return;

        audioMap.get(_id).setLooping(true);


    }

    public boolean IsPlaying(int _id)
    {
        if (!audioMap.containsKey(_id))
            return false;

        return audioMap.get(_id).isPlaying();
    }

    public void Release()
    {
        for (HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }
        audioMap.clear();
    }

}
