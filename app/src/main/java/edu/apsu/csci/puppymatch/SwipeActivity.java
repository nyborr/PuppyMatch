package edu.apsu.csci.puppymatch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SwipeActivity extends Activity {
    int windowWidth;
    int screenCenter;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    public RelativeLayout parentView;
    float alphaValue = 0;
    private Context context;
    MySQLiteHelper db = new MySQLiteHelper(this);

    ArrayList<Animal> AnimalsArrayList;
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);

        context = SwipeActivity.this;
        parentView = (RelativeLayout) findViewById(R.id.swipe_layoutview);


        windowWidth = getWindowManager().getDefaultDisplay().getWidth();

        screenCenter = windowWidth / 2;

        AnimalsArrayList = new ArrayList<>();

        getPuppyData();

        for (int i = 0; i < AnimalsArrayList.size(); i++) {

            LayoutInflater inflate =
                    (LayoutInflater) SwipeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View containerView = inflate.inflate(R.layout.puppy_cardview, null);

            ImageView image = (ImageView) containerView.findViewById(R.id.puppy_image);
            RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);

            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            containerView.setLayoutParams(layoutParams);

            containerView.setTag(i);
            image.setBackgroundResource(AnimalsArrayList.get(i).getPhoto());

            //            m_view.setRotation(i);
            //containerView.setPadding(0, i, 0, 0);

            LayoutParams layoutTvParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


            // ADD dynamically like TextView on image.
            final TextView tvLike = new TextView(context);
            tvLike.setLayoutParams(layoutTvParams);
            tvLike.setPadding(10, 10, 10, 10);
            tvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvLike.setText("MATCHED");
            tvLike.setGravity(Gravity.CENTER);
            tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvLike.setTextSize(25);
            tvLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            tvLike.setX(-20);
            tvLike.setY(100);
            tvLike.setRotation(-50);
            tvLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvLike);

            // ADD dynamically dislike TextView on image.
            final TextView tvUnLike = new TextView(context);
            tvUnLike.setLayoutParams(layoutTvParams);
            tvUnLike.setPadding(10, 10, 10, 10);
            tvUnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvUnLike.setText("SKIP");
            tvUnLike.setGravity(Gravity.CENTER);
            tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvUnLike.setTextSize(25);
            tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            tvUnLike.setX(830);
            tvUnLike.setY(50);
            tvUnLike.setRotation(50);
            tvUnLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvUnLike);

            TextView tvName = (TextView) containerView.findViewById(R.id.name_textview);
            tvName.setText(AnimalsArrayList.get(i).getName());

            TextView sexTV = (TextView) containerView.findViewById(R.id.sex_textview);
            sexTV.setText(AnimalsArrayList.get(i).getGender());

            TextView species = (TextView) containerView.findViewById(R.id.species_textview);
            species.setText(AnimalsArrayList.get(i).getSpecies());

            TextView age = (TextView) containerView.findViewById(R.id.age_textview);
            age.setText(AnimalsArrayList.get(i).getAge() +"");

            TextView size = (TextView) containerView.findViewById(R.id.size_textview);
            if (AnimalsArrayList.get(i).getSize() == Size.LARGE) {
                size.setText("Large");
            } else if (AnimalsArrayList.get(i).getSize() == Size.MEDIUM) {
                size.setText("Medium");
            } else {
                size.setText("Small");
            }

            TextView children = (TextView) containerView.findViewById(R.id.children_textview);
            if (AnimalsArrayList.get(i).isChildren() == true) {
                children.setText("Yes");
            } else {
                children.setText("No");
            }

            final int j = i;

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    containerView.setX(0);
                    containerView.setY(0);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            x = (int) event.getX();
                            y = (int) event.getY();

                            Log.v("On touch", x + " " + y);
                            break;
                        case MotionEvent.ACTION_MOVE:

                            x_cord = (int) event.getRawX();
                            // smoother animation.
                            y_cord = (int) event.getRawY();

                            containerView.setX(x_cord - x);
                            containerView.setY(y_cord - y);

                            if (x_cord >= screenCenter) {
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord > windowWidth - (screenCenter / 1.1)) {
                                    tvLike.setAlpha(1);
                                    if (x_cord > (windowWidth - (screenCenter / 1.1))) {
                                        Likes = 2;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvLike.setAlpha(0);
                                }
                                tvUnLike.setAlpha(0);
                            } else {
                                // rotate image while moving
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord < screenCenter / 1.5) {
                                    tvUnLike.setAlpha(1);
                                    if (x_cord < screenCenter / 1.5) {
                                        Likes = 1;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvUnLike.setAlpha(0);
                                }
                                tvLike.setAlpha(0);
                            }
                            break;
                        case MotionEvent.ACTION_UP:

                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            tvUnLike.setAlpha(0);
                            tvLike.setAlpha(0);

                            if (Likes == 0)
                            {
                                containerView.setX(0);
                                containerView.setY(0);
                                containerView.setRotation(0);
                            } else if (Likes == 1) {
                                parentView.removeView(containerView);
                            } else if (Likes == 2) {
                                Toast.makeText(context, "Added to your matches", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Added to your matches");

                                db.addAnimal(new Animal(AnimalsArrayList.get(j).getId(), AnimalsArrayList.get(j).getName(), AnimalsArrayList.get(j).getGender(), AnimalsArrayList.get(j).getSpecies(), AnimalsArrayList.get(j).getAge(), AnimalsArrayList.get(j).isChildren(), AnimalsArrayList.get(j).getSize(), AnimalsArrayList.get(j).getPhoto()));
                                Log.i("ADDED: " , "Added the animal " + AnimalsArrayList.get(j).getName());
                                parentView.removeView(containerView);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            parentView.addView(containerView);
        }
    }


    private void getPuppyData() {
        Animal ex2 = new Animal(1, "Zelly", "Female","Western Pointer", 5, false, Size.LARGE, R.drawable.zilly);
        ex2.setPhoto(R.drawable.zilly);
        AnimalsArrayList.add(ex2);

        Animal ex1 = new Animal(2, "Walter", "Male","Labrador Retriever, Beagle", 4, true, Size.LARGE, R.drawable.walter);
        ex1.setPhoto(R.drawable.walter);
        AnimalsArrayList.add(ex1);

    }


}
