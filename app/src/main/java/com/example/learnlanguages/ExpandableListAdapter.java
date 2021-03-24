package com.example.learnlanguages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<String>> listOfTopics;
    private List<String> listOfPairs;
    private List<List> listOfLists;
    private Context context;

    ExpandableListAdapter(Context context, List<String> listOfPairs, HashMap<String, List<String>> listOfTopics, List list) {
        this.context = context;
        this.listOfPairs = listOfPairs;
        this.listOfTopics = listOfTopics;
        listOfLists = list;
    }

    @Override
    public int getGroupCount() {
        return listOfPairs.size();

    }

    @Override
    public int getChildrenCount(int i) {
        if(listOfTopics.size()>0)
            return listOfTopics.get(listOfPairs.get(i)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int i) {
        return listOfPairs.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listOfTopics.get(listOfPairs.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_adapter_list_group, null);
        }
        TextView lilListHeader = view.findViewById(R.id.textView_expandable_list_adapter_list_group);
        lilListHeader.setText(headerTitle);
        lilListHeader.setCursorVisible(true);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String childText = (String)getChild(i, i1);
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_adapter_list_item, null);
        }
        TextView txtListChild = view.findViewById(R.id.textView_expandable_list_adapter_list_item);
        txtListChild.setText(childText.replace("_", " "));
        ImageView imageView = view.findViewById(R.id.imageView_expandable_list_adapter_list_item);
        imageView.setImageResource(R.drawable.expandable_list_adapter_check_box_outline);
        for(int k = 0; k< listOfLists.size(); k++){
            if(listOfLists.get(k).get(0).equals(i) && listOfLists.get(k).get(1).equals(i1))
                imageView.setImageResource(R.drawable.expandable_list_adapter_check_box);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}