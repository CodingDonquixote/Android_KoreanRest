package com.jbckss.koreanrest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestInfo extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    String restName;
    double geoV;
    double geoV1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rest_info, container, false);
        restName = getArguments().getString("no1");
        Log.d("에러 확인", "RestInfo.java_onCreateView : restName = "+restName);
        String rest_call = getArguments().getString("no3");
        String rest_add = getArguments().getString("no4");
        geoV = getArguments().getDouble("geo_v");
        geoV1 = getArguments().getDouble("geo_v1");

        TextView callContent = (TextView) view.findViewById(R.id.callContent);
        callContent.setText(rest_call);
        TextView addContent = (TextView) view.findViewById(R.id.addressContent);
        addContent.setText(rest_add);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(geoV, geoV1);
        Log.d("맵 좌표 확인","구글맵 좌표 값 : v = "+geoV+", v1 = "+geoV1);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(restName);
        //markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}
