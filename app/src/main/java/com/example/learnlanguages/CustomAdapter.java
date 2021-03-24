package com.example.learnlanguages;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    CustomAdapter(Context context, List listOfTabels) {
        super(context, R.layout.custom_adapter, listOfTabels);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckysInflanter__1 = LayoutInflater.from(getContext());
        View customView = buckysInflanter__1.inflate(R.layout.custom_adapter, parent, false);
        String getPosition = getItem(position);
        TextView textView = customView.findViewById(R.id.textView_custom_adapter);
        textView.setText(getPosition);
        return customView;
    }
}