package com.example.myapplicationmaps;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    Spinner spnMapas;
    EditText edtLatitude, edtLongitude, edtTitulo;
    Button btnCarregarGPS, btnInserirPin, btnSalvarMapa;
    GoogleMap mapa;
    LocationManager minhaPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        checarPermissoes();

        spnMapas = findViewById(R.id.spnMapas);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongitude = findViewById(R.id.edtLongitude);
        edtTitulo = findViewById(R.id.edtTitulo);
        btnCarregarGPS = findViewById(R.id.btnCarregarGPS);
        btnInserirPin = findViewById(R.id.btnInserirPin);
        btnSalvarMapa = findViewById(R.id.btnSalvarMapa);

        btnCarregarGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPosicao();
            }
        });

        btnInserirPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meuTitulo = edtTitulo.getText().toString();
                double minhaLatitude = Double.parseDouble(edtLatitude.getText().toString());
                double minhaLongitude = Double.parseDouble(edtLongitude.getText().toString());
                inserirPin(minhaLatitude, minhaLongitude, meuTitulo);
            }
        });

        btnSalvarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
    }


    @Override
    public void onLocationChanged(Location location) {
        edtLatitude.setText(location.getLatitude() + "");
        edtLongitude.setText(location.getLongitude() + "");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void inserirPin(double latitude, double longitude, String titulo) {
        LatLng local = new LatLng(latitude, longitude);
        mapa.addMarker(
                new MarkerOptions().position(local).title(titulo).
                        icon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        );
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 10));
    }

    private void checarPermissoes() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void atualizarPosicao() {
        try {
            minhaPosicao = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            minhaPosicao.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 5, this);
        } catch (SecurityException e){

        }
    }

}








