package com.wmjulio.projetoandroid2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Bundle bundle;

    private double lat, lon;
    private TextView tvNome, tvDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        bundle = getIntent().getExtras();
        try {
            lat = Double.parseDouble(bundle.getString("DetailsObjLat"));
            lon = Double.parseDouble(bundle.getString("DetailsObjLon"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvNome = findViewById(R.id.tvNomeDetalhes);
        tvDetalhes = findViewById(R.id.tvDescDetalhes);
        Log.w("BUNDLE", bundle.getString("DetailsObjNome"));
        Log.w("BUNDLE", bundle.getString("DetailsObjDesc"));
        tvNome.setText(bundle.getString("DetailsObjNome"));
        tvDetalhes.setText(bundle.getString("DetailsObjDesc"));


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        LatLng posicao = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().position(posicao).title("Tá aqui môfí"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicao, 18));
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(posicao, posicao));
        mMap.setMinZoomPreference(5);
    }
}
