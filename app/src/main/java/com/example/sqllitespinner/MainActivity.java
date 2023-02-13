package com.example.sqllitespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText name;
    Button subBtn;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextInputEditText) findViewById(R.id.text_input);
        subBtn = (Button) findViewById(R.id.submit_btn);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String label = name.getText().toString();
                if (label.trim().length()>0){
                    DbHandler dbHandler = new DbHandler(getApplicationContext());
                    dbHandler.insertLabel(label);

                    name.setText("");

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(name.getWindowToken(),0);

                    loadSpinnerData();
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter Label Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadSpinnerData() {

        DbHandler dbHandler = new DbHandler(getApplicationContext());
        List<String> labels = dbHandler.getLables();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,labels);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

        String label = parent.getItemAtPosition(pos).toString();

        Toast.makeText(parent.getContext(), "You selected "+label, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}