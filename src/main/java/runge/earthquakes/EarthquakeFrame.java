package runge.earthquakes;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.swing.*;
import java.awt.*;
import java.io.*;


public class EarthquakeFrame extends JFrame
{
    public EarthquakeFrame() throws IOException {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JLabel earthquakePlace = new JLabel();
        earthquakePlace.setFont(new Font("Serif", Font.PLAIN, 36));
        earthquakePlace.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(earthquakePlace, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setSize(500, 500);
        setTitle("Earthquake Status");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();


        EarthquakeService earthquakeService = retrofit.create(EarthquakeService.class);

        Disposable disposable = earthquakeService.getLatestEarthquakes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        featureCollection ->
                        {
                            String location = featureCollection.features[0].properties.place;
                            earthquakePlace.setText(location);
                        },

                        Throwable::printStackTrace
                );

    }
}
