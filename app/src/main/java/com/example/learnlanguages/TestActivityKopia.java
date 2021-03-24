package com.example.learnlanguages;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivityKopia extends AppCompatActivity {
    private List<String> copyDrawList, selectedWords4;
    private List<List> drawList, resultList;
    private TextView tvQuantityOfWords, tvLanguagesOfPair, tvTopic, tvWord;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private int quantityOfWords, word, selectedWord = 0;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        DatabaseHelper db = new DatabaseHelper(this);
        copyDrawList = new ArrayList<>(); selectedWords4 = new ArrayList<>();
        drawList = new ArrayList<>(); resultList = new ArrayList<>();random = new Random();
        String[] tableOfAddedTables = getIntent().getStringArrayExtra("tableOfAddedTables");
        String[] tableOfAddedPairs = getIntent().getStringArrayExtra("tableOfAddedPairs");
        for(String j: tableOfAddedPairs){
            String b = j.replace(" ", "").replace("-","");
            for(String i: tableOfAddedTables){
                Cursor cursor = db.selectData(i);
                String c = i.substring(0, b.length());
                if(c.equals(b)){
                    String d = i.substring(b.length());
                    while (cursor.moveToNext()) {
                        List<String> a = new ArrayList<>();
                        a.add(j);a.add(d);
                        a.add(cursor.getString(1));
                        a.add(cursor.getString(2));
                        drawList.add(a);
                    }
                }
            }
        }
        quantityOfWords = drawList.size();
        tvWord = findViewById(R.id.textViewWord_test_activity);
        tvQuantityOfWords = findViewById(R.id.textViewQuantityOfWords_test_activity);
        tvLanguagesOfPair = findViewById(R.id.textViewLanguagesPair_test_activity);
        tvTopic = findViewById(R.id.textViewTopic_test_activity);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        if(drawList.size()>4)
            showView();
        else {
            setInvisible();
            tvQuantityOfWords.setPadding(0,400,0,0);
            tvQuantityOfWords.setGravity(Gravity.CENTER);
            tvQuantityOfWords.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tvQuantityOfWords.setText("Wybrane tematy zawierają\nzbyt mało słów\naby rozpocząć test");
        }
    }

    private void showView(){
        if(drawList.size()> copyDrawList.size()){
            selectedWord = selectedWord+1;
            tvQuantityOfWords.setText(selectedWord +"/"+ quantityOfWords);
            while(true){
                boolean k = true;
                word = random.nextInt(drawList.size());
                if(copyDrawList.contains(drawList.get(word).get(0).toString()+drawList.get(word).get(1).toString()+drawList.get(word).get(2).toString()))
                    k=false;
                if(k)
                    break;
            }
            selectedWords4.add(drawList.get(word).get(2).toString());
            tvLanguagesOfPair.setText(drawList.get(word).get(0).toString());
            tvTopic.setText(drawList.get(word).get(1).toString().replace("_", " "));
            tvWord.setText(drawList.get(word).get(2).toString());
            int selectCheckBox = random.nextInt(4)+1;
            if(selectCheckBox==1){
                checkBox1.setText(drawList.get(word).get(3).toString());
                drawWords(checkBox2, drawList);
                drawWords(checkBox3, drawList);
                drawWords(checkBox4, drawList);
            }
            else if(selectCheckBox==2){
                checkBox2.setText(drawList.get(word).get(3).toString());
                drawWords(checkBox1, drawList);
                drawWords(checkBox3, drawList);
                drawWords(checkBox4, drawList);
            }
            else if(selectCheckBox==3){
                checkBox3.setText(drawList.get(word).get(3).toString());
                drawWords(checkBox1, drawList);
                drawWords(checkBox2, drawList);
                drawWords(checkBox4, drawList);
            }
            else if(selectCheckBox==4){
                checkBox4.setText(drawList.get(word).get(3).toString());
                drawWords(checkBox1, drawList);
                drawWords(checkBox2, drawList);
                drawWords(checkBox3, drawList);
            }
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTheResult(checkBox1);
                    setFalse();
                    copyDrawList.add(drawList.get(word).get(0).toString()+drawList.get(word).get(1).toString()+drawList.get(word).get(2).toString());
                    selectedWords4.clear();
                    showView();
                }});
            checkBox2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTheResult(checkBox2);
                    setFalse();
                    copyDrawList.add(drawList.get(word).get(0).toString()+drawList.get(word).get(1).toString()+drawList.get(word).get(2).toString());
                    selectedWords4.clear();
                    showView();
                }
            });
            checkBox3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTheResult(checkBox3);
                    setFalse();
                    copyDrawList.add(drawList.get(word).get(0).toString()+drawList.get(word).get(1).toString()+drawList.get(word).get(2).toString());
                    selectedWords4.clear();
                    showView();
                }
            });
            checkBox4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTheResult(checkBox4);
                    setFalse();
                    copyDrawList.add(drawList.get(word).get(0).toString()+drawList.get(word).get(1).toString()+drawList.get(word).get(2).toString());
                    selectedWords4.clear();
                    showView();
                }});
        }
        else {
            setContentView(R.layout.activity_end);
            setInvisible();
            int realAnswers = 0;
            for(int i = 0; i< resultList.size(); i++){
                if(resultList.get(i).get(2)=="true")
                    realAnswers=realAnswers+1;
            }
            int percent2= 100*realAnswers / drawList.size();
            TextView tvQuantity = findViewById(R.id.textViewQuantity_activity_end);
            TextView tvPercent = findViewById(R.id.textViewPercent_activity_end);
            tvQuantity.setText(realAnswers+"/"+ drawList.size());
            tvPercent.setText(percent2+"%");
            ListAdapter listAdapter = new MyAdapter(this, resultList);
            ListView listView = findViewById(R.id.listView_activity_end);
            listView.setAdapter(listAdapter);
        }
    }

    private void drawWords(CheckBox checkBox, List<List> list){
        while (true){
            int number = random.nextInt(list.size());
            if(!selectedWords4.contains(list.get(number).get(2).toString())){
                selectedWords4.add(list.get(number).get(2).toString());
                checkBox.setText(list.get(number).get(3).toString());
                break;
            }
        }
    }

    private void setFalse(){
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
    }

    private void checkTheResult(CheckBox a){
        List<String> b = new ArrayList<>();
        b.add(drawList.get(word).get(2).toString());
        b.add(a.getText().toString());
        if(drawList.get(word).get(3).equals(a.getText()))
            b.add("true");
        else
            b.add("false");
        resultList.add(b);
    }

    private void setInvisible() {
        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        tvLanguagesOfPair.setVisibility(View.INVISIBLE);
        tvTopic.setVisibility(View.INVISIBLE);
        tvWord.setVisibility(View.INVISIBLE);
    }

    public void quit(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            finishAffinity();
    }

    public void playAgain(View view) {
        super.onBackPressed();
    }
}