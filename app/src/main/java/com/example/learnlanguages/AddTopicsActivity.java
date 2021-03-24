package com.example.learnlanguages;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AddTopicsActivity extends AppCompatActivity implements AddNewTopic, RemoveTopic {
    private String languagesPair, newVariable, topicName, language1, language2;
    private List listOfTopics;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_topics_activity);
        listOfTopics = new ArrayList();
        showNameTopics();
        selectLanguagesPairsTopics();
    }

    private void showNameTopics(){
        languagesPair = getIntent().getStringExtra("pair");
        modifyVariables();
        TextView textView = findViewById(R.id.textView_add_topics_activity);
        textView.setText("Lista tematów\n"+ languagesPair);
        db = new DatabaseHelper(this);
    }

    private void showTopics(){
        for(int i = 0; i<db.selectTableList().size(); i++){
            newVariable = languagesPair;
            newVariable = newVariable.replace(" ", "").replace("-", "");
            String name = db.selectTableList().get(i).substring(0, newVariable.length());
            if (newVariable.equals(name))
                listOfTopics.add(db.selectTableList().get(i).substring(newVariable.length()).replace("_", " "));
        }
        ListView listView = findViewById(R.id.listView_add_topics_activity);
        ListAdapter listAdapter = new CustomAdapter(this, listOfTopics);
        listView.setAdapter(listAdapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                topicName = listOfTopics.get(i).toString().replace(" ", "_");
                newActivity(newVariable + listOfTopics.get(i).toString().replace(" ", "_"), topicName);
            }
        });
    }

    private void selectLanguagesPairsTopics() {
        if(db.selectTableList().size()>0)
            showTopics();
        else{
            showTopics();
            Toast.makeText(AddTopicsActivity.this, "The list is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewTopicDialog(View view) {
        AddNewTopicDialog addNewTopicDialog = new AddNewTopicDialog();
        addNewTopicDialog.languagesPair = languagesPair;
        addNewTopicDialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void removeTopic() {
        db.dropTable(language1 + language2 + topicName);
        listOfTopics.clear();
        selectLanguagesPairsTopics();
    }

    private void newActivity(String putExtra1, String putExtra2){
        Intent intent = new Intent(this, AddWordsActivity.class);
        intent.putExtra("newVariable", putExtra1);
        intent.putExtra("topicName", putExtra2);
        intent.putExtra("language1", language1);
        intent.putExtra("language2", language2);
        startActivity(intent);
    }

    private void removeTopicDialog(){
        RemoveTopicDialog removeTopicDialog = new RemoveTopicDialog();
        removeTopicDialog.topicName = topicName;
        removeTopicDialog.show(getSupportFragmentManager(),"");
    }

    private void modifyVariables(){
        languagesPair = languagesPair.replace(" ","");
        int pause = languagesPair.indexOf("-");
        language1 = languagesPair.substring(0, pause);
        language2 = languagesPair.substring(pause+1);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.add_languages_pair_activity_add_topisc_activity_popupmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getItemId() == R.id.deleteOption){
            topicName = listOfTopics.get(info.position).toString();
            removeTopicDialog();
            return true;
        }
        else
            return super.onContextItemSelected(item);
    }

    @Override
    public void addTopic(String newTopic) {
        newTopic = newTopic.toLowerCase();
        if(!db.isExist("sqlite_master", "name", newTopic)){
            modifyVariables();
            db.addTable(newTopic, language1, language2);
            listOfTopics.clear();
            showNameTopics();
            selectLanguagesPairsTopics();
        }
        else
            Toast.makeText(AddTopicsActivity.this, "!!! The given topic already exists !!!", Toast.LENGTH_SHORT).show();
    }
}