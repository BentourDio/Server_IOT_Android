package com.example.newtabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Option2 extends AppCompatActivity {

    public String state;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_option2 );

        Spinner mySpinner = (Spinner) findViewById( R.id.spinner );

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Option2.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray( R.array.spinner_names ));

        myAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        mySpinner.setAdapter( myAdapter );

        Button submitText = findViewById( R.id.submitItem );
        System.out.println("Hello from onCLick");

        submitText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                System.out.println("Hello from onCLick");
                Option1 temp = new Option1();
                state = mySpinner.getSelectedItem().toString();
                String[] split_location = state.split(",");


                temp.setCoordinates(split_location[0],split_location[1] );
                System.out.println("state is ....."+state);

                Toast.makeText( getApplicationContext(),state,Toast.LENGTH_SHORT ).show();

            }
        } );


    }
}