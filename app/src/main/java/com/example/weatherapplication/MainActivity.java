package com.example.weatherapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.example.weatherapplication.viewmodel.MyViewModel;

import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    CalendarView clndr;
    AppCompatButton addNote;

    ImageView search;
    EditText etCity;
    Button btnWeather, btnCalendar;
    TextView city, country, time, temp, forecast, humidity, min_temp, max_temp, sunrises, sunsets;


    String API = "----- YOUR API KEY HERE -----";
    String CITY;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clndr = (CalendarView) findViewById(R.id.calendarid);
        addNote = (AppCompatButton) findViewById(R.id.addnote);

        // line for creating the view model
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // var to store the date off the calendar
        String[] dateup = new String[1];

        // we define a OnChangeListener for the calendar ( every time you change the date we save it)
        clndr.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/" + dayOfMonth;
                dateup[0] = date;
            }
        });

        // we define an OnClickListener for the add note button
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("date", dateup[0]);
                startActivity(intent);
            }
        });

        // switching between screens (calendar & weather) *****************************************

        // we start by hidding the calendarand add note button
        findViewById(R.id.calendar_layout).setVisibility(View.GONE);
        findViewById(R.id.addnote).setVisibility(View.GONE);

        btnWeather = findViewById(R.id.btnWeather);
        btnCalendar = findViewById(R.id.btnCalendar);

        // the weather button OnClickListener
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.calendar_layout).setVisibility(View.GONE);
                findViewById(R.id.addnote).setVisibility(View.GONE);
                findViewById(R.id.weather_layout).setVisibility(View.VISIBLE);
            }
        });

        // the calendar button OnClickListener
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.calendar_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.addnote).setVisibility(View.VISIBLE);
                findViewById(R.id.weather_layout).setVisibility(View.GONE);
            }
        });


        // weather layout


        search = findViewById(R.id.btnSearch);
        etCity = findViewById(R.id.etCity);

        city = (TextView) findViewById(R.id.city);
        country = (TextView) findViewById(R.id.country);
        time = (TextView) findViewById(R.id.time);
        temp = (TextView) findViewById(R.id.temp);
        forecast = (TextView) findViewById(R.id.forecast);
        humidity = (TextView) findViewById(R.id.humidity);
        min_temp = (TextView) findViewById(R.id.min_temp);
        max_temp = (TextView) findViewById(R.id.max_temp);
        sunrises = (TextView) findViewById(R.id.sunrises);
        sunsets = (TextView) findViewById(R.id.sunsets);

        // the search button OnClickListener
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // we retrieve the input from the edit text
                CITY = etCity.getText().toString();
                // and we execute the asynchrone class
                new weatherTask().execute();
            }
        });


    }


    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject sys = jsonObj.getJSONObject("sys");

                // CALL VALUE IN API :

                String city_name = jsonObj.getString("name");
                String countryname = sys.getString("country");
                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temperature = main.getString("temp");
                String cast = weather.getString("description");
                String humi_dity = main.getString("humidity");
                String temp_min = main.getString("temp_min");
                String temp_max = main.getString("temp_max");
                Long rise = sys.getLong("sunrise");
                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
                Long set = sys.getLong("sunset");
                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));

                // SET ALL VALUES IN TEXTBOX :

                city.setText(city_name);
                country.setText(countryname);
                time.setText(updatedAtText);
                temp.setText(temperature + "°C");
                forecast.setText(cast);
                humidity.setText(humi_dity);
                min_temp.setText(temp_min);
                max_temp.setText(temp_max);
                sunrises.setText(sunrise);
                sunsets.setText(sunset);

            } catch (Exception e) {

                Toast.makeText(MainActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();

            }

        }

    }
}
