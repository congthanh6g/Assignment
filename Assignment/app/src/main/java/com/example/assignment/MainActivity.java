package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvHour;
    private TextView tvNumb;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumb = (TextView)findViewById(R.id.tvNumb);
        tvStatus = (TextView)findViewById(R.id.tvStatus);

        //b1:
        getHours();

        //b2:
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //b3:


        //b4:
        rvHour = (RecyclerView)findViewById(R.id.rvHour);
        rvHour.setLayoutManager(layoutManager);

    }


    private void getHours()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dataservice.accuweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager service = retrofit.create(ApiManager.class);

        service.gethour().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if (response.body() != null) {
                    List<Weather> listweather = response.body();

                    HourAdapter adapter = new HourAdapter(MainActivity.this, listweather);
                    rvHour.setAdapter(adapter);

                    Weather weather = listweather.get(0);
                    tvNumb.setText(weather.getTemperature().getValue().intValue() + "");
                    tvStatus.setText(weather.getWeatherIcon());
                }
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t+ "" , Toast.LENGTH_LONG).show();
            }
        });
    }
}