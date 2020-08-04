package com.droids.apppermissionexample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.droids.apppermission.AppPermissionHandler;
import com.droids.apppermission.AppPermissions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestPhone(View view) {

        AppPermissions.check(this, Manifest.permission.CALL_PHONE, null, new AppPermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Phone granted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                View view = MainActivity.this.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(view, "Permission Denied plz try again....", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    public void requestCameraAndStorage(View view) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        AppPermissions.check(this, permissions, null, null, new AppPermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Camera+Storage granted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestLocation(View view) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        AppPermissions.Options options = new AppPermissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        AppPermissions.check(this, permissions, rationale, options, new AppPermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Location granted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Location denied.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSettings(View view) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }
}