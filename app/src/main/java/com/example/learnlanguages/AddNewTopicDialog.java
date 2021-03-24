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

public class AddNewTopicDialog extends AppCompatDialogFragment {
    private AddNewTopic addNewTopic;
    private EditText editText;
    String languagesPair;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_new_topic_dialog, null);
        builder.setView(view)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText = view.findViewById(R.id.editText_add_new_topic);
                        languagesPair = languagesPair.replace(" ","");
                        languagesPair = languagesPair.replace("-","");
                        if(editText.length()!=0)
                            addNewTopic.addTopic(languagesPair + editText.getText().toString().replace(" ", "_"));
                    }
                })
                .setNegativeButton("BACK",null);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addNewTopic = (AddNewTopic) context;
    }
}