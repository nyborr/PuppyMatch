package edu.apsu.csci.puppymatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class DogProfileActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name_key");
        String species = intent.getStringExtra("species_key");
        String age = intent.getStringExtra("age_key");
    }
}
