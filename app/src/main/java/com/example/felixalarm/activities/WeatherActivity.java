package com.example.felixalarm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.felixalarm.R;
import com.example.felixalarm.adapters.NotesAdapter;
import com.example.felixalarm.adapters.WeatherAdapter;
import com.example.felixalarm.adapters.WeatherModal;
import com.example.felixalarm.entities.Note;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class WeatherActivity extends AppCompatActivity {

    private EditText inputCity;
    private TextView cityNameText, temperatureText, conditionText, humidityText, pressureText, windSpeedText;
    private ImageView iconImage;
    private ImageView searchImage;
    private GifImageView gifBack;
    ImageView settingsImage;
    Button btGifs, btImages;



    private RecyclerView weatherRecyclerView;
    private List<WeatherModal> weatherList;
    private WeatherAdapter weatherAdapter;

    String descriptionT;
    boolean isThemeChanged = false;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "d2d4dc6c3e99f743a99857ee56a2e875";

//    JSONObject saved = new JSONObject();
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        inputCity = findViewById(R.id.inputCity);
        cityNameText = findViewById(R.id.textCityName);
        temperatureText = findViewById(R.id.textTemperature);
        conditionText = findViewById(R.id.textCondition);
        iconImage = findViewById(R.id.imageIcon);
        searchImage = findViewById(R.id.imageSearch);
        gifBack = findViewById(R.id.gifback);
        humidityText = findViewById(R.id.textHumidity);
        pressureText = findViewById(R.id.textPressure);
        windSpeedText = findViewById(R.id.textWindSpeed);
        settingsImage = findViewById(R.id.imageSettings);
        btGifs = findViewById(R.id.btGifs);
        btImages = findViewById(R.id.btImages);

        Intent intent = getIntent();
        boolean isOpened = intent.getBooleanExtra("flag", false);

        if (isOpened == true) {
            Intent i = new Intent(getApplicationContext(), AlarmOnActivity.class);
            startActivity(i);
        }

        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.layoutPopUpWindow).setVisibility(View.VISIBLE);

                btGifs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gifBack.setVisibility(View.VISIBLE);
                        switch (descriptionT) {
                            case "clear sky":
                                gifBack.setImageResource(R.drawable.sun_clouds);
                                break;
                            case "few clouds": //серые тучки, но с солнышком кек
                                gifBack.setImageResource(R.drawable.sky_clouds);
                                break;
                            case "scattered clouds":  //серые тучки без грозы(без черых тучек)
                            case "broken clouds":
                                gifBack.setImageResource(R.drawable.half_gray_clouds); //текстура half gray clouds кривая
                                break;
                            case "overcast clouds": //с черными тучками!
                                gifBack.setImageResource(R.drawable.gray_clouds);//overcast n broker have same icon
                                break;
                            case "light rain": //слабенький дождь
                                gifBack.setImageResource(R.drawable.raindrops);
                                break;
                            case "moderate rain": //умеренный дождь
                                gifBack.setImageResource(R.drawable.hard_rain);
                                break;
                        }
                        isThemeChanged = false;
                        findViewById(R.id.layoutPopUpWindow).setVisibility(View.INVISIBLE);
                    }
        });
                btImages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gifBack.setVisibility(View.VISIBLE);
                        gifBack.setAlpha(0.9F);
                        switch (descriptionT) {
                            case "clear sky":
                                gifBack.setImageResource(R.drawable.sunny_w);
                                gifBack.setAlpha(0.5F);
                                break;
                            case "few clouds": //серые тучки, но с солнышком кек
                            case "scattered clouds":  //серые тучки без грозы(без черых тучек)
                            case "broken clouds":
                            case "overcast clouds": //с черными тучками!
                                gifBack.setImageResource(R.drawable.cloudy_w);//overcast n broker have same icon
                                break;
                            case "light rain": //слабенький дождь
                            case "moderate rain": //умеренный дождь
                                gifBack.setImageResource(R.drawable.snowy_w); //потом изменим на rainy(когда Аня нарисует кек)
                                break;
                        }
                        isThemeChanged = true;
                        findViewById(R.id.layoutPopUpWindow).setVisibility(View.INVISIBLE);
                    }
                });

            }});

        //потом переделаю, чтобы оновсе отображалось не в текствью, а в ресайклервью



//        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
//        weatherRecyclerView.setLayoutManager(
//                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
//        );
//
//        weatherList = new ArrayList<>();
//        weatherRecyclerView.setAdapter(weatherAdapter);




        inputCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                getWeather();
//                saveWeather();
                return false;
            }
        });

        inputCity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    getWeather();
//                    String s = inputCity.getText().toString().trim();
//                    if (!s.equals("")) {
//                        try {
//                            if (!preferences.getString("saved", "").equals("")) {
//                                saved = new JSONObject(preferences.getString("saved", ""));
//                            }
//                            saved.put("saved"+saved.length(),s);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("testing", saved+"");
//                        editor.putString("saved", saved.toString());
//                        editor.apply();
//                        inputCity.setText("");
//                    }
//                    saveWeather();
                    return true;
                }
                return false;
            }
        });

        //норм вариант(+-)

//        inputCity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                getWeather();
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_weather);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_alarm:

                        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                        intent.putExtra("description", descriptionT);
                        intent.putExtra("theme", isThemeChanged);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        return true;
                    case R.id.nav_notes:
//                        startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                        Intent intent2 = new Intent(getApplicationContext(), NotesActivity.class);
                        intent2.putExtra("description", descriptionT);
                        intent2.putExtra("theme", isThemeChanged);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_weather:
                        return true;
                }
                return false;
            }
        });

//        init();
//        Intent intent = getIntent();
//        if (intent.getIntExtra("position", -1) != -1) {
//            try {
//                String s = inputCity.getText().toString().trim();
//                if (!preferences.getString("saved", "").equals("")) {
//                    saved = new JSONObject(preferences.getString("saved", ""));
//                }
//                inputCity.setText(saved.getString("saved"+intent.getIntExtra("position", 0)));
//                s = saved.getString("saved"+intent.getIntExtra("position", 0));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

    }

//    private void init() {
//        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
//        editor = preferences.edit();
//
//        inputCity = findViewById(R.id.inputCity);
//        cityNameText = findViewById(R.id.textCityName);
//
//
//
//    }

//    public void saveWeather(){
//
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        editor.putString("CITY_NAME", cityNameText.getText().toString());
//        editor.commit();
//
////        editor.putString(String.valueOf(cityNameText), String.valueOf(cityNameText));
//    }


    public void getWeather() {
        String tempUrl = "";
        String city = inputCity.getText().toString().trim();

        if (city.equals("")) {
            Toast.makeText(this, "City field can't be empty!", Toast.LENGTH_LONG).show();
        } else {
//            https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");

                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");

                    descriptionT = description;

                    String icon = jsonObjectWeather.getString("icon");

                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");

                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");

                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");

                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");

                    //бета версия, отображение всего в 1 TV
//                    output += "Current weather of " + cityName + ":"
//                            + "\n temperature: " + df.format(temp) + " °C"
//                            + "\n feels like: " + df.format(feelsLike) + " °C"
//                            + "\n conditions: " + description;
//                    conditionText.setText(output);

                    cityNameText.setText(cityName);
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    String tempT = decimalFormat.format(temp);
                    temperatureText.setText(tempT + " °C");

                    conditionText.setText(description);

                    String humidityT=String.valueOf(humidity);
                    humidityText.setText("Humidity: " + humidityT + " %");

                    String pressureT = String.valueOf(pressure);
                    pressureText.setText("Pressure: " + pressureT + " Pa");

                    windSpeedText.setText("Wind speed: " + wind + " m/s");

                    gifBack.setVisibility(View.VISIBLE);
                    switch (description) {
                        case "clear sky":
                            iconImage.setImageResource(R.drawable.sun);
                            gifBack.setImageResource(R.drawable.sun_clouds);
                            break;
                        case "few clouds": //серые тучки, но с солнышком кек
                            iconImage.setImageResource(R.drawable.cloudy);
                            gifBack.setImageResource(R.drawable.sky_clouds);
                            break;
                        case "scattered clouds":  //серые тучки без грозы(без черых тучек)
                        case "broken clouds":
                            iconImage.setImageResource(R.drawable.cloud);
                            gifBack.setImageResource(R.drawable.half_gray_clouds); //текстура half gray clouds кривая
                            break;
                        case "overcast clouds": //с черными тучками!
                            iconImage.setImageResource(R.drawable.cloud_black);
                            gifBack.setImageResource(R.drawable.gray_clouds);//overcast n broker have same icon
                            break;
                        case "light rain": //слабенький дождь
                            iconImage.setImageResource(R.drawable.cloudy_rain);
                            gifBack.setImageResource(R.drawable.raindrops);
                            break;
                        case "moderate rain": //умеренный дождь
                            iconImage.setImageResource(R.drawable.rainy);
                            gifBack.setImageResource(R.drawable.hard_rain);
                            break;

                            //need to add snowy weather
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}
