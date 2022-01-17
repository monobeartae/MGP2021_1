package com.example.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class GameWinDialog extends DialogFragment {

    public static boolean isShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        isShown = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Game Cleared!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isShown = false;
                        GamePage.Instance.EndGame();
                    }
                });

        return builder.create();
    }
}
