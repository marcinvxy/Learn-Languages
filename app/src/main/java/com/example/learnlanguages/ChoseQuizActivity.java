package com.example.learnlanguages;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.example.learnlanguages.R.font.gabriola;

public class ChoseQuizActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Switch switch1, switch2;
    private boolean test = true, quiz = false;
    private ExpandableListView expandablelistView;
    private List<List> listOfList;
    private List<Integer> listOfDeveloped;
    private HashMap<String, List<String>> listOfTopics;
    private List<String> listOfPairs, listOfTable;
    private ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_quiz_activity);
        db = new DatabaseHelper(this);
        listOfList = new ArrayList<>();
        listOfDeveloped = new ArrayList<>();
        listOfTopics = new HashMap<>();
        listOfPairs = new ArrayList<>();
        listOfTable = new ArrayList<>();
        listOfTable = new ArrayList<>();
        listAdapter = new ExpandableListAdapter(this, listOfPairs, listOfTopics, listOfList);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch1.setTypeface(ResourcesCompat.getFont(this, gabriola));
        switch2.setTypeface(ResourcesCompat.getFont(this, gabriola));
        switch1.setChecked(true);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switch2.setChecked(false);
                    test = true;quiz = false;
                }
                else {
                    switch2.setChecked(true);
                    test = false;quiz = true;
                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switch1.setChecked(false);
                    test = false;quiz = true;
                }
                else {
                    switch1.setChecked(true);
                    test = true;
                    quiz = false;
                }
            }
        });
        showView();
    }

    private void showView(){
        expandablelistView = findViewById(R.id.expandableListView);
        expandablelistView.setAdapter(listAdapter);
        addToListOfTopics();
        expandablelistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(listOfDeveloped.contains(i))
                    listOfDeveloped.remove(listOfDeveloped.indexOf(i));
                else
                    listOfDeveloped.add(i);
                Collections.sort(listOfDeveloped);
                return false;
            }
        });
        expandablelistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String nameOfAddTable =
                        expandablelistView.getExpandableListAdapter().getGroup(i).toString().replace(" ",
                        "").replace("-",
                                "")+expandablelistView.getExpandableListAdapter().getChild(i, i1);
                if(listOfTable.indexOf(nameOfAddTable) == -1)
                    listOfTable.add(nameOfAddTable);
                else
                    listOfTable.remove(nameOfAddTable);

                if(listOfList.size()>0){
                    boolean a = false;
                    for(int k = 0; k< listOfList.size(); k++){
                        if(listOfList.get(k).get(0).equals(i) && listOfList.get(k).get(1).equals(i1)){
                            a = true;
                            listOfList.remove(k);
                        }
                    }
                    if(!a){
                        List<Integer> list1 = new ArrayList<>();
                        list1.add(i);
                        list1.add(i1);
                        listOfList.add(list1);
                    }
                }
                else {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(i1);
                    listOfList.add(list);
                }
                listOfPairs.clear();
                listOfTopics.clear();
                showView();
                for(int j = 0; j< listOfDeveloped.size(); j++){
                    expandableListView.expandGroup(listOfDeveloped.get(j));
                }
                return false;
            }
        });
    }

    private void addToListOfTopics() {
            Cursor cursor = db.selectFromTheTable();
            while (cursor.moveToNext()) {
                listOfPairs.add(cursor.getString(1));
            }
            for (int i = 0; i < listOfPairs.size(); i++){
                List<String> listOfAddedOnes = new ArrayList();
                String languagesPair = listOfPairs.get(i).replace(" ", "").replace("-", "");
                for(String k: db.selectTableList()){
                    if(languagesPair.equals(k.substring(0, languagesPair.length()))){
                        listOfAddedOnes.add(k.substring(languagesPair.length()));
                    }
                    listOfTopics.put(listOfPairs.get(i), listOfAddedOnes);
                }
            }
    }

    public void nextActivity(View view){
        if(listOfTable.size()>0){
            Set<String> setOfNextPairs = new HashSet<>();
            for(String r: listOfPairs){
                String a = r.replace(" ", "").replace("-",
                        "");
                for(String e: listOfTable){
                    if(a.equals(e.substring(0, a.length()))){
                        setOfNextPairs.add(r);}}}
            String[] tableOfAddedTables = new String[listOfTable.size()];
            for(int y = 0; y< listOfTable.size(); y++){
                tableOfAddedTables[y] = listOfTable.get(y);
            }
            String[] tableOfAddedPairs = new String[setOfNextPairs.size()];
            int q = 0;
            for(String w: setOfNextPairs){
                tableOfAddedPairs[q] = w;
                q = q+1;
            }
            if(test){
                Intent intent = new Intent(this, TestActivity.class);
                intent.putExtra("tableOfAddedTables", tableOfAddedTables);
                intent.putExtra("tableOfAddedPairs", tableOfAddedPairs);
                startActivity(intent);
            }
            else if(quiz){
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("tableOfAddedTables", tableOfAddedTables);
                intent.putExtra("tableOfAddedPairs", tableOfAddedPairs);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(ChoseQuizActivity.this, "No topic selected !!!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}