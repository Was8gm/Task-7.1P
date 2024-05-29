package com.example.task71p;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup selectionType;
    private EditText editName, editPhone, editDescription, editDate, editLocation;
    private Button btnSubmit, btnGetLocation;
    private DatabaseHelper databaseHelper;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        selectionType = findViewById(R.id.selection_type);
        editName = findViewById(R.id.edit_Name);
        editPhone = findViewById(R.id.edit_Phone);
        editDescription = findViewById(R.id.edit_Description);
        editDate = findViewById(R.id.edit_Date);
        editLocation = findViewById(R.id.edit_Location);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetLocation = findViewById(R.id.btnGetLocation);

        databaseHelper = new DatabaseHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = selectionType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String type = selectedRadioButton.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String description = editDescription.getText().toString();
                String date = editDate.getText().toString();
                String location = editLocation.getText().toString();

                if (databaseHelper.addItem(type, name, phone, description, date, location)) {
                    Toast.makeText(CreateAdvertActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(CreateAdvertActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        editLocation.setText(address.getAddressLine(0));
                        Toast.makeText(CreateAdvertActivity.this, "Location updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}