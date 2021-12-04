package com.example.finding_minimum_way_algorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finding_minimum_way_algorithm.algorithm.MinimumPath;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[][] array;
    private final List<Points> dataPoint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text_view);

        // Location of places to be delivered by couriers
        dataPoint.add(new Points(41, 69));
        dataPoint.add(new Points(23, 45));
        dataPoint.add(new Points(2435, 2425));
        dataPoint.add(new Points(12, 56));
        dataPoint.add(new Points(2425, 2424));

        ///
        textView.setOnClickListener(view -> {
            MinimumPath minimumPath = new MinimumPath();
            array = null;
            array = new int[dataPoint.size() + 1][dataPoint.size() + 1];
            int[] arr;

            // A matrix is ​​created from the distance between each point
            for (int i = 1; i <= dataPoint.size(); i++) {
                for (int j = 1; j <= dataPoint.size(); j++) {
                    array[i][j] = getDistance(
                            dataPoint.get(i - 1).getLatitude(),
                            dataPoint.get(j - 1).getLatitude(),
                            dataPoint.get(i - 1).getLongitude(),
                            dataPoint.get(j - 1).getLongitude());
                }
            }
            for (int i = 1; i <= dataPoint.size(); i++) {
                for (int j = 1; j <= dataPoint.size(); j++) {
                    if (array[i][j] == 1 && array[j][i] == 0) {
                        array[j][i] = 1;
                    }
                }
            }

            //we send the distance to the algorithm to calculate the graphs
            arr = minimumPath.mparray(array);

            /// Here the value of "arr [i] - 1" is equal to the index of dataPoint

            StringBuilder s = new StringBuilder();
            for (int i = 0; i < arr.length-1; i++) {
                s.append(arr[i] - 1).append(" ");
            }

            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        });
    }


    public int getDistance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        return (int) distance;

    }
}

class Points {
    double latitude;
    double longitude;

    public Points(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}