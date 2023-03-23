package com.example.weatherapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.weatherapplication.entité.FragTableDataset;
import com.example.weatherapplication.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

public class Activity3 extends AppCompatActivity {

    FloatingActionButton backtoact2;
    AppCompatButton deletebutt, confirmbtnfrag;
    LinearLayout deletecont;

    EditText name, friends;
    TimePicker tp;
    MyViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_layout);

        backtoact2 = (FloatingActionButton) findViewById(R.id.back_act2);
        deletebutt = (AppCompatButton) findViewById(R.id.deletebutton);
        deletecont = (LinearLayout) findViewById(R.id.deletecont);
        confirmbtnfrag = (AppCompatButton) findViewById(R.id.confirmbtnfrag);

        name = (EditText) findViewById(R.id.sortiename);
        friends = (EditText) findViewById(R.id.friends);
        tp = (TimePicker) findViewById(R.id.timepick);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        if(getIntent().getExtras().getBoolean("showdelete")){
            deletebutt.setVisibility(View.VISIBLE);

            FragTableDataset frag = viewModel.getFrag(getIntent().getExtras().getInt("idoffrag1"));

            name.setText(frag.getName());
            friends.setText(frag.getFriends());

            String hour = (frag.getHour()).split(":", 2)[0];
            String min = (frag.getHour()).split(":", 2)[1];
            tp.setCurrentHour(Integer.parseInt(hour));
            tp.setCurrentMinute(Integer.parseInt(min));


            deletebutt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent deletingthefrag = new Intent();

                    viewModel.deletingfrag(frag);

                    setResult(10, deletingthefrag);
                    finish();
                }
            });

            //code for submitting the data after confirm click to database

            confirmbtnfrag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent confirmingintent1 = new Intent();

                    viewModel.updatedata(name.getText().toString(), (Integer.toString(tp.getCurrentHour()) +":"+ Integer.toString(tp.getCurrentMinute())),friends.getText().toString(), getIntent().getExtras().getInt("idoffrag1"));

                    setResult(20, confirmingintent1);
                    finish();
                }
            });

        }else {
            if (getIntent().getExtras().getBoolean("hiddendelete")) {
                deletebutt.setVisibility(View.GONE);
                deletecont.setVisibility(View.GONE);

                confirmbtnfrag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent confirmingintent2 = new Intent();

                        FragTableDataset entity = new FragTableDataset(name.getText().toString(), (Integer.toString(tp.getCurrentHour()) +":"+ Integer.toString(tp.getCurrentMinute())),friends.getText().toString(), getIntent().getExtras().getString("date3"));
                        viewModel.creedata(entity);


                        setResult(RESULT_OK, confirmingintent2);
                        finish();
                    }
                });
            }
        }

        backtoact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnintent = new Intent();
                setResult(RESULT_CANCELED, returnintent);
                finish();
            }
        });


    }
}
