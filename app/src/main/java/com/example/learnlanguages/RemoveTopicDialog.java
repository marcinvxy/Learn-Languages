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

public class RemoveTopicDialog extends AppCompatDialogFragment {
    private RemoveTopic removeTopic;
    String topicName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remove_topic_dialog, null);
        TextView textView = view.findViewById(R.id.textView_remove_topic_dialog);
        textView.setText("Czy napewno chcesz\n usunąć temat "+ topicName +"?\n\n Usunie to również wszystkie\n zapisane w nim słówka!");
        builder.setView(view)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeTopic.removeTopic();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        removeTopic = (RemoveTopic) context;
    }
}