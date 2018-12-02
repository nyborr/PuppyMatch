package edu.apsu.csci.puppymatch;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MatchesActivity extends ListActivity {

    public final String NAME_KEY = "name_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        MySQLiteHelper db = new MySQLiteHelper(this);

        List<Animal> list = db.getAllAnimals();

        for (Animal animal : list) {
            Log.i("LISTINGITEMS: ", animal.getName());
        }

//
//
//        ListView listView = findViewById(R.id.mylistview);
//        ArrayAdapter<Animal> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//
//        setListAdapter(adapter);

    }



}
