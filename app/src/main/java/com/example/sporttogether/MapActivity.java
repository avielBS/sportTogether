package com.example.sporttogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button locationButton;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationButton = findViewById(R.id.map_location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                double[] arr = new double[2];

                arr[0] = location.latitude;
                arr[1] = location.longitude;

                resultIntent.putExtra(Util.LOCATION,arr);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });

        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        this.location = new LatLng(31.2643411,35.005801);
        final MarkerOptions markerOptions = new MarkerOptions().position(location).title("workout");
        final Marker marker = mMap.addMarker(markerOptions);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                location=latLng;
                marker.setPosition(latLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( latLng, 15));

                Toast.makeText(
                        MapActivity.this,
                        "Lat : " + latLng.latitude + " , "
                                + "Long : " + latLng.longitude,
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
