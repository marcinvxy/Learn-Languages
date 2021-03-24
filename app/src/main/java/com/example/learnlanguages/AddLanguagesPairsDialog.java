package com.example.learnlanguages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddLanguagesPairsDialog extends AppCompatDialogFragment {
    private AddLanguagesPairs addLanguagesPairs;
    private EditText editText1, editText2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_languages_pairs_dialog, null);
        builder.setView(view)
                .setPositiveButton("ADD",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText1 = view.findViewById(R.id.editText1_add_languages_pairs_dialog);
                        editText2 = view.findViewById(R.id.editText2_add_languages_pairs_dialog);
                        if (editText1.length()!=0 && editText2.length()!=0)
                            addLanguagesPairs.addPair(editText1.getText().toString(), editText2.getText().toString());
                    }
                })
                .setNegativeButton("BACK",null);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addLanguagesPairs = (AddLanguagesPairs) context;
    }
}