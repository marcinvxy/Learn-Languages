package com.example.learnlanguages;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.List;

public class MyAdapter extends ArrayAdapter<List> {
    MyAdapter(Context context, List listOfAdded) {
        super(context, R.layout.my_adapter, listOfAdded);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.my_adapter, parent, false);
        String word = getItem(position).get(0).toString()+" - "+getItem(position).get(1).toString();
        TextView textView = view.findViewById(R.id.textView_my_adapter);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textView.setText(word);

        if(getItem(position).get(2).toString() == "true")
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        else if(getItem(position).get(2).toString() == "false")
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));

        return view;
    }
}