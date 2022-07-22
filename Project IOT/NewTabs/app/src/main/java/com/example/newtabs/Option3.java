package com.example.newtabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Option3 extends  AppCompatActivity {

    TextView sensor;
    Button setRange,addTab;
    EditText min,max;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_option3 );




        sensor = (TextView)findViewById( R.id.sensor );



        setRange = (Button)findViewById( R.id.set );
        min = (EditText)findViewById( R.id.min );
        max = (EditText)findViewById( R.id.max );

        setRange.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                //save edit text to string
                String minSave = min.getText().toString();

                int minValue = 0;

                minValue = Integer.valueOf( min.getText().toString() );



                //save edit text to string
                String maxSave = max.getText().toString();

                int maxValue = 0;

                maxValue = Integer.valueOf( max.getText().toString() );

                Toast.makeText( Option3.this, "Range set", Toast.LENGTH_LONG ).show();
            }
        } );


        Spinner opt3Spinner = (Spinner) findViewById( R.id.spinner );

        ArrayAdapter<String> opt3Adapter = new ArrayAdapter<String>(Option3.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray( R.array.sensorTypes ));

        opt3Adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        opt3Spinner.setAdapter( opt3Adapter );

        Button chooseSensor = findViewById( R.id.sensorSelect );

        chooseSensor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String state = opt3Spinner.getSelectedItem().toString();

                sensor.setText( opt3Spinner.getSelectedItem().toString() );
                Toast.makeText( getApplicationContext(),state,Toast.LENGTH_SHORT ).show();
            }
        } );
    }

}