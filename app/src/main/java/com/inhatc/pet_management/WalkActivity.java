package com.inhatc.pet_management;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class WalkActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        // 위치 서비스 클라이언트 초기화
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 위치 권한 확인 및 요청
        checkLocationPermission();

        // 위치 업데이트 시작
        startLocationUpdates();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void startLocationUpdates() {
        // 위치 업데이트를 시작하는 코드를 여기에 추가하세요.
    }

    // 위치 정보 엑세스 권한 요청
    private final ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean fineLocationGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                Boolean coarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);

                if (fineLocationGranted != null && fineLocationGranted) {
                    // Precise location access granted
                    startLocationUpdates();

                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    startLocationUpdates();
                    // Only approximate location access granted

                } else {
                    Toast.makeText(this,
                            "Unable to launch app because location permissions are denied.",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                }
            }
    );

    private void checkLocationPermission() {
        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean fineLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;


        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see (link)Request permissions.(구글개발자)
        if (!coarseLocationGranted && !fineLocationGranted) {
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            startLocationUpdates();
        }
    }

    // NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때 호출
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.556, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국 수도");

        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
    }
}