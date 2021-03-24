package com.example.learnlanguages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddWordsDialog extends AppCompatDialogFragment {
    String topicName, tableName, language1, language2;
    private EditText editText, editText1;
    private AddWords addWords;
    private DatabaseHelper db;
    private Button button;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_words_dialog, null);
        TextView textView = view.findViewById(R.id.textView_add_words_dialog);
        textView.setText(topicName);
        editText = view.findViewById(R.id.editText1_add_words_dialog);
        editText.setHint(language1);
        editText1 = view.findViewById(R.id.editText2_add_words_dialog);
        editText1.setHint(language2);
        builder.setView(view)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addWords.back();
                    }
                });
        button = view.findViewById(R.id.button_add_words_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length()>0 && editText1.getText().length()>0){
                    if(!db.isExist(tableName, language1, editText.getText().toString())){
                        db.insertIntoTheTable(tableName, editText.getText().toString(), editText1.getText().toString(), language1, language2);
                        editText.setText("");
                        editText1.setText("");
                        editText.jumpDrawablesToCurrentState();
                    }
                    else
                        Toast.makeText(getContext(), "!!! Podane słowo już istnieje !!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "!!! Wprowadzono nieprawidłowe dane !!!", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addWords = (AddWords) context;
    }
}