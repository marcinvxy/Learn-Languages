package com.example.learnlanguages;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AddWordsActivity extends AppCompatActivity implements AddWords {
    private List<String> selectedList, listOfWords;
    private String tableName, topicName, language1, language2;
    private ImageButton imageButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_words_activity);
        selectedList = new ArrayList();
        listOfWords = new ArrayList();
        tableName = getIntent().getStringExtra("newVariable");
        topicName = getIntent().getStringExtra("topicName");
        language1 = getIntent().getStringExtra("language1");
        language2 = getIntent().getStringExtra("language2");
        TextView textView = findViewById(R.id.textView_add_words_activity);
        textView.setText(topicName.replace("_", " "));
        db = new DatabaseHelper(this);
        showWords();
        imageButton = findViewById(R.id.toolbar_add_words_activity_xml);
        imageButton.setImageResource(R.drawable.add_words_activity_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedList.size()==0)
                    addWordsDialog();
                else {
                    removeWords();
                    selectedList.clear();
                    listOfWords.clear();
                    selectWords();
                }
                if(selectedList.size()==0)
                    imageButton.setImageResource(R.drawable.add_words_activity_add);
                else
                    imageButton.setImageResource(R.drawable.add_words_activity_delete);
            }});
    }

    private void showWords(){
        if (db.selectWordsFromTheTable(tableName, language1, language2).size()>0)
            selectWords();
        else{
            selectWords();
            Toast.makeText(AddWordsActivity.this,
                    "No words have been added yet\n           Add new words!!!", Toast.LENGTH_SHORT).show();
        }
    }


    private void addWordsDialog(){
        AddWordsDialog addWordsDialog = new AddWordsDialog();
        addWordsDialog.topicName = topicName;
        addWordsDialog.language1 = language1;
        addWordsDialog.language2 = language2;
        addWordsDialog.tableName = tableName;
        addWordsDialog.show(getSupportFragmentManager(),"");
    }

    private void removeWords(){
        for(String i: selectedList){
            String value = i.substring(0, i.indexOf("-")-1);
            db.deleteValue(tableName, language1, value);
        }
        listOfWords.clear();
        showWords();
    }

    private void selectWords(){
        for(int i = 0; i<db.selectWordsFromTheTable(tableName, language1, language2).size(); i++){
            listOfWords.add(db.selectWordsFromTheTable(tableName, language1, language2).get(i).toString());
        }
        ListView listView = findViewById(R.id.listView_add_words_activity);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.add_word_activity_check_box,
                R.id.check_add_word_activity, listOfWords);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = ((TextView)view).getText().toString();
                if(selectedList.contains(selected))
                    selectedList.remove(selected);
                else
                    selectedList.add(selected);

                if(selectedList.size()==0)
                    imageButton.setImageResource(R.drawable.add_words_activity_add);
                else
                    imageButton.setImageResource(R.drawable.add_words_activity_delete);
            }
        });
    }

    @Override
    public void back() {
        listOfWords.clear();
        showWords();
    }
}