package runge.earthquakes;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class EarthquakeFrame extends JFrame
{
    public EarthquakeFrame() throws IOException {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Gson gson = new Gson();
        FeatureCollection featureCollection = gson.fromJson(bufferedReader, FeatureCollection.class);

        JLabel header = new JLabel("Latest Earthquake:");
        header.setFont(new Font("Serif", Font.ITALIC, 50));
        Font font = header.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        header.setFont(font.deriveFont(attributes));
        header.setVerticalAlignment(JLabel.TOP);
        header.setHorizontalAlignment(JLabel.CENTER);

        JLabel earthquakePlace = new JLabel(featureCollection.features[0].properties.place);
        earthquakePlace.setFont(new Font("Serif", Font.ROMAN_BASELINE, 36));
        earthquakePlace.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(earthquakePlace, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setSize(500, 500);
        setTitle("Earthquake Status");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}