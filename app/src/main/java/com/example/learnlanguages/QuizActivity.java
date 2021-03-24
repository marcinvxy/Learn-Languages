package com.example.learnlanguages;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private List<List> drawList, resultList;
    private EditText editText;
    private TextView tvQuantityOfWords, tvLanguagesOfPair, tvTopic, tvWord;
    private int selectedWord, quantityOfWords, word;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        DatabaseHelper db = new DatabaseHelper(this);
        resultList = new ArrayList<>();drawList = new ArrayList<>();random = new Random();
        String[] tableOfAddedTables = getIntent().getStringArrayExtra("tableOfAddedTables");
        String[] tableOfAddedPairs = getIntent().getStringArrayExtra("tableOfAddedPairs");
        for(String j: tableOfAddedPairs){
            String b = j.replace(" ", "").replace("-","");
            for(String i: tableOfAddedTables){
                Cursor cursor = db.selectData(i); String c = i.substring(0, b.length());
                if(c.equals(b)){
                    String d = i.substring(b.length());
                    while (cursor.moveToNext()) {
                        List<String> a = new ArrayList<>();
                        a.add(j);
                        a.add(d);
                        a.add(cursor.getString(1));
                        a.add(cursor.getString(2));
                        drawList.add(a);
                    }
                }
            }
        }
        quantityOfWords = drawList.size(); tvWord = findViewById(R.id.textViewWord_quiz_activity);
        tvQuantityOfWords = findViewById(R.id.textViewQuantityOfWords_quiz_activity);
        tvLanguagesOfPair = findViewById(R.id.textViewLanguagesPairs_quiz_activity);
        tvTopic = findViewById(R.id.textViewTopic_quiz_activity);
        editText = findViewById(R.id.editText_quiz_activity);
        showView();
    }

    private void showView(){
        if(drawList.size()>0){
            editText.setHint("answer");
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    next();
                    if(drawList.size()==0)
                        return false;
                    else
                        return true;
                }
            });
            selectedWord = selectedWord+1;
            tvQuantityOfWords.setText(selectedWord +"/"+ quantityOfWords);
            word = random.nextInt(drawList.size());
            tvLanguagesOfPair.setText(drawList.get(word).get(0).toString());
            tvTopic.setText(drawList.get(word).get(1).toString().replace("_", " "));
            tvWord.setText(drawList.get(word).get(2).toString());
        }
        else {
            editText.setVisibility(View.INVISIBLE);
            tvQuantityOfWords.setPadding(0,400,0,0);
            tvQuantityOfWords.setGravity(Gravity.CENTER);
            tvQuantityOfWords.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tvQuantityOfWords.setText("The selected topics contain\ntoo few words\nto start the test");
        }
    }

    private void next(){
        if(editText.getText().length()!=0){
            List<String> b = new ArrayList<>();
            b.add(drawList.get(word).get(2).toString());
            b.add(editText.getText().toString());

            if(drawList.get(word).get(3).equals(editText.getText().toString()))
                b.add("true");
            else
                b.add("false");

            resultList.add(b);
            editText.setText("");
            drawList.remove(word);

            if(drawList.size()!=0)
                showView();
            else{
                setContentView(R.layout.activity_end);
                int realAnswers = 0;
                for(int i = 0; i< resultList.size(); i++){
                    if(resultList.get(i).get(2)=="true")
                        realAnswers=realAnswers+1;
                }
                int procent= 100*realAnswers/ quantityOfWords;
                TextView tvQuantity = findViewById(R.id.textViewQuantity_activity_end);
                TextView tvPercent = findViewById(R.id.textViewPercent_activity_end);
                tvQuantity.setText(realAnswers+"/"+ drawList.size());
                tvPercent.setText(procent+"%");
                ListAdapter listAdapter = new MyAdapter(this, resultList);
                ListView listView = findViewById(R.id.listView_activity_end);
                listView.setAdapter(listAdapter);
            }
        }
        else
            Toast.makeText(QuizActivity.this, "Enter the answer!!!", Toast.LENGTH_SHORT).show();
    }

    public void quit(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            finishAffinity();
    }

    public void playAgain(View view) {
        super.onBackPressed();
    }
}