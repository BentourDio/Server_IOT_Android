package com.example.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class stime extends AppCompatActivity {
    Button okbtn;
    EditText s_time;
    String timesave;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView( R.layout.s_time );

        s_time = (EditText) findViewById(R.id.sampletime);

        okbtn = (Button) findViewById( R.id.button_ok );

        okbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                timesave = s_time.getText().toString();
                Intent intent= new Intent();
                intent.putExtra("Sample Time", timesave);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

}
