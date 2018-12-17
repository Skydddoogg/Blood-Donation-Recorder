package com.nawinc27.mac.blooddonorfrontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nawinc27.mac.blooddonorfrontend.form.FormSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new FormSender())
                    .addToBackStack(null)
                    .commit();
        }
    }
}