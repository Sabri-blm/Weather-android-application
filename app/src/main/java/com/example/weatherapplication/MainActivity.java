package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.entité.FragTableDataset;
import com.example.weatherapplication.viewmodel.MyViewModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    CalendarView clndr;
    //TextView txt;
    AppCompatButton addNote;
    //LiveData<List<FragTableDataset>> all;
    //List<FragTableDataset> all_values;

    Button btnSearch;
    EditText etCity;
    ImageView iconWeather;
    TextView tvTemp, textDay, tvState, weekly;

    RelativeLayout relativeLay;

    ListView lvDailyWeather;


    String ApiKey = "f77dffe03357c19ac842f8ca72293c8c";
    float lat,lon;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clndr = (CalendarView) findViewById(R.id.calendarid);
        //txt = (TextView) findViewById(R.id.txt);
        addNote = (AppCompatButton) findViewById(R.id.addnote);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        String[] dateup = new String[1];

        //dateup[0] = Long.toString(clndr.getDate());

        clndr.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/" + dayOfMonth;
                //txt.setText(date);
                dateup[0] = date;
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("date", dateup[0]);
                startActivity(intent);
            }
        });

        // API Manipulation *****************************************


        // Split Screen
        findViewById(R.id.calendar_layout).setVisibility(View.GONE);
        findViewById(R.id.addnote).setVisibility(View.GONE);
        relativeLay = findViewById(R.id.relativeLay);
        relativeLay.setVisibility(View.GONE);
        weekly = findViewById(R.id.weekly);
        weekly.setVisibility(View.GONE);

        Button btnWeather = null, btnCalendar = null;

        btnWeather = findViewById(R.id.btnWeather);

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.calendar_layout).setVisibility(View.GONE);
                findViewById(R.id.addnote).setVisibility(View.GONE);
                findViewById(R.id.weather_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.lvDailyWeather).setVisibility(View.VISIBLE);
                relativeLay.setVisibility(View.VISIBLE);
                weekly.setVisibility(View.VISIBLE);
            }
        });

        btnCalendar = findViewById(R.id.btnCalendar);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.calendar_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.addnote).setVisibility(View.VISIBLE);
                findViewById(R.id.weather_layout).setVisibility(View.GONE);
                findViewById(R.id.lvDailyWeather).setVisibility(View.GONE);
                relativeLay.setVisibility(View.GONE);
                weekly.setVisibility(View.GONE);
            }
        });


        //API for Current Daily Weather


        btnSearch = findViewById(R.id.btnSearch);
        etCity = findViewById(R.id.etCity);
        iconWeather = findViewById(R.id.iconWeather);
        tvTemp = findViewById(R.id.tvTemp);
        tvState = findViewById(R.id.tvState);
        lvDailyWeather = findViewById(R.id.lvDailyWeather);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = etCity.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                    relativeLay.setVisibility(View.GONE);
                } else {

                    // float[] param = nameToParam(city);
                    loadWeatherByCityName(city);
                    relativeLay.setVisibility(View.VISIBLE);
                    weekly.setVisibility(View.VISIBLE);


                }
            }
        });


    }

    /* private float[] nameToParam(String city) {

        Ion.with(this)
                .load("http://api.openweathermap.org/geo/1.0/direct?q="+city+"&appid="+ApiKey)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if (e != null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // convert json response to Java

                            JsonObject laton = result.get("lat").getAsJsonObject();
                            JsonObject lonon = result.get("lon").getAsJsonObject();

                            float lat = laton.getAsFloat();
                            float lon = lonon.getAsFloat();


                        }
                    }
                });

        return new float[] {lat,lon};
    }
    */

        private void loadWeatherByCityName (String city){
            Ion.with(this)
                    .load("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&&units=metric&appid=" + ApiKey)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (e != null) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {

                                JsonObject main = result.get("main").getAsJsonObject();
                                double temp = main.get("temp").getAsDouble();
                                tvTemp.setText(temp + "°C");

                                JsonArray weather = result.get("weather").getAsJsonArray();
                                String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                                loadIcon(icon);

                                String desc = weather.get(0).getAsJsonObject().get("description").getAsString();
                                tvState.setText(desc);

                                loadDailyForecast(city);


                            }

                        }
                    });
        }

        private void loadDailyForecast (String city){
            Ion.with(this)
                    .load("https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&&units=metric&appid=" + ApiKey)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error
                            if (e != null) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {

                                List<Weather> weatherList = new ArrayList<>();
                                //String timeZone = result.get("timezone").getAsString();
                                JsonArray list = result.get("list").getAsJsonArray();
                                for (int i = 3; i < list.size(); i = i + 8) {
                                    //Long date = list.get(i).getAsJsonObject().get("dt").getAsLong();
                                    Double temp = list.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
                                    String icon = list.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();

                                    weatherList.add(new Weather(temp, icon));

                                }

                                // attach adapter to ListView
                                DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(MainActivity.this, weatherList);

                                lvDailyWeather.setAdapter(dailyWeatherAdapter);


                            }

                        }


                    });

        }



    private void loadIcon(String icon) {
        Ion.with(this)
                .load("http://openweathermap.org/img/w/"+icon+".png")
                .intoImageView(iconWeather);
    }

}
