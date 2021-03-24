package com.example.learnlanguages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RemoveLanguagesPairDialog extends AppCompatDialogFragment {
    private RemoveLanguagesPair removeLanguagesPair;
    String langauagesPair;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remove_languages_pair_dialog, null);
        TextView textView = view.findViewById(R.id.textView_remove_languages_pair_dialog);
        textView.setText("Czy napewno chcesz\n usunąć parę\n"+ langauagesPair +"?\n\n Usunie to również wszystkie\n zapisane w niej tamety \noraz słówka!");
        builder.setView(view)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeLanguagesPair.removePair(langauagesPair);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        removeLanguagesPair = (RemoveLanguagesPair) context;
    }
}