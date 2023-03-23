package com.example.weatherapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapplication.entité.FragTableDataset;
import com.example.weatherapplication.viewmodel.MyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

public class Activity2 extends AppCompatActivity {

    FloatingActionButton backtoact1, addFrag1;
    MyViewModel viewModel;
    TextView txt22;

    List<FragTableDataset> all_values;
    FragmentManager manager;
    FragmentTransaction transaction;
    String date2;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);

        backtoact1 = (FloatingActionButton) findViewById(R.id.back_act2);
        addFrag1 = (FloatingActionButton) findViewById(R.id.addFrag);
        txt22 = (TextView) findViewById(R.id.txt2);

        date2 = getIntent().getExtras().getString("date");

        txt22.setText(date2);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        all_values = viewModel.getalldata(date2);

        this.manager = getSupportFragmentManager();
        this.transaction = manager.beginTransaction();

        for(FragTableDataset x : all_values){
            this.transaction.add(R.id.linearlayoutofhorizantalscroll, sortie.newInstance(x.getName(),x.getFriends(),x.getHour(), x.getId()), Integer.toString(x.getId()));
        }

        this.transaction.commit();


        backtoact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Activity2.this, Activity3.class);
                intent1.putExtra("hiddendelete", true);
                intent1.putExtra("date3", date2);
                startActivityForResult(intent1, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                all_values = viewModel.getalldata(date2);
                sortie a;



                for(FragTableDataset x : all_values) {
                    a = (sortie) manager.findFragmentByTag(Integer.toString(x.getId()));
                    if (a == null) {
                        transaction.add(R.id.linearlayoutofhorizantalscroll, sortie.newInstance(x.getName(), x.getFriends(), x.getHour(), x.getId()), Integer.toString(x.getId()));
                    }
                }


                transaction.commit();

                /*Intent i = new Intent(Activity2.this, Activity2.class);
                i.putExtra("date", date2);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);*/

            }
        }
    }
}

