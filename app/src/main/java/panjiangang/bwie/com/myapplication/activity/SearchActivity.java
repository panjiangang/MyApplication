package panjiangang.bwie.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import panjiangang.bwie.com.myapplication.R;

public class SearchActivity extends AppCompatActivity {
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText sousuo = findViewById(R.id.search_edit_sousuo);
        Button btn = findViewById(R.id.search_btn_sousuo);
        ImageButton imageButton = findViewById(R.id.search_imgbtn_return);
        final TextView textView = findViewById(R.id.search_tv_text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = sousuo.getText().toString().trim();
                textView.append("\n" + s);
                Intent intent = new Intent(SearchActivity.this, SearchShowActivity.class);
                intent.putExtra("name", s);
                startActivity(intent);

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }
}
