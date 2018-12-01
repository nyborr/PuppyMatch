package edu.apsu.csci.puppymatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.swipe_button);
        b.setOnClickListener(this);

        b = findViewById(R.id.about_button);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        if (v.getId() == R.id.swipe_button) {
            intent = new Intent(getApplicationContext(), SwipeActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), AboutActivity.class);
        }

        startActivity(intent);
    }
}

