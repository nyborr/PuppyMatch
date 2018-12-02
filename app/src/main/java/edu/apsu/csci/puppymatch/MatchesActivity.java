package edu.apsu.csci.puppymatch;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

       // ArrayAdapter<Animal> adapter = new myAdapter(this, list);
       // setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Animal animal = (Animal) l.getItemAtPosition(position);
        String gender = animal.getSpecies();

        //Intent intent = new Intent(getApplicationContext(), StateActivity.class);
        // If serializable dont do this
//        intent.putExtra(NAME_KEY, state.getName());
//        intent.putExtra(ABBV_KEY, state.getAbbrv());

        //intent.putExtra(STATE_KEY, state);


        //startActivity(intent);

    }


    class myAdapter extends ArrayAdapter<Animal> {

        private final Animal[] animals;

        public myAdapter(Context context, Animal[] animals) {
            super(context, R.layout.row, animals);
            this.animals = animals;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = layoutInflater.inflate(R.layout.row, parent, false);

            TextView nameTV = rowView.findViewById(R.id.animal_name_textview);
            nameTV.setText(animals[position].getName());
            TextView speciesTV = rowView.findViewById(R.id.gender_textView);
            speciesTV.setText(animals[position].getSpecies());

            return rowView;
        }
    }

}
