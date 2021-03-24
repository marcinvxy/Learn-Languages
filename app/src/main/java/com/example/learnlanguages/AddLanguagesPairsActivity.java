package com.example.learnlanguages;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AddLanguagesPairsActivity extends AppCompatActivity implements AddLanguagesPairs, RemoveLanguagesPair {
    private List list;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_languages_pairs_activity);
        db = new DatabaseHelper(this);
        list = new ArrayList();
        selectLanguagesPairs();
        showLanguagePairs();
    }

    private void selectLanguagesPairs() {
        Cursor cursor = db.selectFromTheTable();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(1));
        }
    }

    private void showLanguagePairs() {
        ListView listView = findViewById(R.id.listView_add_languages_pairs_activity);
        ListAdapter listAdapter = new CustomAdapter(this, list);
        listView.setAdapter(listAdapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        newActivity((String) list.get(i));
                    }
                });
    }

    public void addLanguagesPairsDialog(View view) {
        AddLanguagesPairsDialog addLanguagesPairsDialog = new AddLanguagesPairsDialog();
        addLanguagesPairsDialog.show(getSupportFragmentManager(), "");
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
            removeLanguagesPairDialog(list.get(info.position).toString());
            return true;
        }
        else
            return super.onContextItemSelected(item);
    }

    @Override
    public void addPair(String language1, String language2) {
        String joinLanguages = language1 + " - " + language2;
        joinLanguages = joinLanguages.toLowerCase();
        if(!db.isExist("pairs", "languages_pairs", joinLanguages)){
            db.addLanguangePair(joinLanguages);
            list.clear();
            selectLanguagesPairs();
            showLanguagePairs();
        }
        else
            Toast.makeText(AddLanguagesPairsActivity.this, "!!! The given language pair already exists !!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removePair(String pair) {
        db.deleteValue("pairs", "languages_pairs", pair);
        pair = pair.replace(" ","").replace("-", "");
        List<String> pairsToRemove = new ArrayList();
        for(int i = 0; i<db.selectTableList().size(); i++){
            if(pair.equals(db.selectTableList().get(i).substring(0, pair.length())))
                pairsToRemove.add(db.selectTableList().get(i));
        }

        for(String i: pairsToRemove){
            db.dropTable(i);
        }
        pairsToRemove.clear();
        list.clear();
        selectLanguagesPairs();
        showLanguagePairs();
    }

    private void removeLanguagesPairDialog(String languagesPair){
        RemoveLanguagesPairDialog removeLanguagesPairDialog = new RemoveLanguagesPairDialog();
        removeLanguagesPairDialog.langauagesPair = languagesPair;
        removeLanguagesPairDialog.show(getSupportFragmentManager(),"");
    }

    private void newActivity(String valuePutExtra) {
        Intent intent = new Intent(this, AddTopicsActivity.class);
        intent.putExtra("pair", valuePutExtra);
        startActivity(intent);
    }
}