package edu.apsu.csci.puppymatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MatchesDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create matches table
        String CREATE_BOOK_TABLE = "CREATE TABLE matches ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "sex TEXT," +
                "species TEXT," +
                "age INTEGER," +
                "children BOOLEAN," +
                "size TEXT ," +
                "photo INTEGER)";

        // create matches table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS matches");

        // create fresh books table
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_MATCHES = "matches";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_SPECIES = "species";
    private static final String KEY_AGE = "age";
    private static final String KEY_CHILDREN = "children";
    private static final String KEY_SIZE = "size";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_SHELTERNAME  = "sheltername";
    private static final String KEY_SHELTERADDRESS = "shelteraddress";
    private static final String KEY_SHELTERPHONE = "shelterphone";
    private static final String KEY_ADOPTIONFEE = "adoptionfee";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_SEX, KEY_SPECIES, KEY_AGE, KEY_CHILDREN, KEY_SIZE, KEY_PHOTO};


    public void addAnimal(Animal animal){
        //for logging
        Log.d("addAnimal", animal.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, animal.getName());
        values.put(KEY_SEX, animal.getGender());
        values.put(KEY_SPECIES, animal.getSpecies());
        values.put(KEY_AGE, animal.getAge());
        values.put(KEY_CHILDREN, animal.isChildren());

        if (animal.getSize() == Size.LARGE) {
            values.put(KEY_SIZE, "Large");
        } else if (animal.getSize() == Size.MEDIUM) {
            values.put(KEY_SIZE, "Medium");
        } else {
            values.put(KEY_SIZE, "Small");
        }
        values.put(KEY_PHOTO, animal.getPhoto());
        values.put(KEY_SHELTERNAME, animal.getShelterName());
        values.put(KEY_SHELTERADDRESS, animal.getShelterAddress());
        values.put(KEY_SHELTERPHONE, animal.getShelterPhone());
        values.put(KEY_ADOPTIONFEE, animal.getAdoptionFee());

        // 3. insert
        db.insert(TABLE_MATCHES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Animal getAnimal(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MATCHES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Animal animal = new Animal();
        animal.setId(Integer.parseInt(cursor.getString(0)));
        animal.setName(cursor.getString(1));
        animal.setGender(cursor.getString(2));
        animal.setSpecies(cursor.getString(3));
        animal.setAge(cursor.getString(4));
        animal.setChildren(cursor.getInt(4) > 0);
        if (cursor.getString(5) == "Small") {
            animal.setSize(Size.SMALL);
        } else if (cursor.getString(5) == "Medium") {
            animal.setSize(Size.MEDIUM);
        } else if (cursor.getString(5) == "large") {
            animal.setSize(Size.LARGE);
        }
        animal.setPhoto(cursor.getInt(6));
        animal.setShelterName(cursor.getString(7));
        animal.setShelterAddress(cursor.getString(8));
        animal.setShelterPhone(cursor.getString(9));
        animal.setAdoptionFee(cursor.getString(10));

        Log.d("getAnimal("+id+")", animal.toString());

        // 5. return animal
        return animal;
    }

    // Get All Books
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new LinkedList<Animal>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MATCHES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Animal animal = null;
        if (cursor.moveToFirst()) {
            do {
                animal = new Animal();
                animal.setId(Integer.parseInt(cursor.getString(0)));
                animal.setId(Integer.parseInt(cursor.getString(0)));
                animal.setName(cursor.getString(1));
                animal.setGender(cursor.getString(2));
                animal.setSpecies(cursor.getString(3));
                animal.setAge(cursor.getString(4));
                animal.setChildren(cursor.getInt(4) > 0);
                if (cursor.getString(5) == "Small") {
                    animal.setSize(Size.SMALL);
                } else if (cursor.getString(5) == "Medium") {
                    animal.setSize(Size.MEDIUM);
                } else if (cursor.getString(5) == "large") {
                    animal.setSize(Size.LARGE);
                }
                animal.setPhoto(cursor.getInt(6));

                // Add book to books
                animals.add(animal);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", animals.toString());

        // return books
        return animals;
    }

    // Updating single book
    public int updateAnimal(Animal animal) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, animal.getName());
        values.put(KEY_SEX, animal.getGender());
        values.put(KEY_SPECIES, animal.getSpecies());
        values.put(KEY_AGE, animal.getAge());
        values.put(KEY_CHILDREN, animal.isChildren());

        if (animal.getSize() == Size.LARGE) {
            values.put(KEY_SIZE, "Large");
        } else if (animal.getSize() == Size.MEDIUM) {
            values.put(KEY_SIZE, "Medium");
        } else {
            values.put(KEY_SIZE, "Small");
        }
        values.put(KEY_PHOTO, animal.getPhoto());


        // 3. updating row
        int i = db.update(TABLE_MATCHES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(animal.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single animal
    public void deleteAnimal(Animal animal) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MATCHES,
                KEY_ID+" = ?",
                new String[] { String.valueOf(animal.getId()) });

        // 3. close
        db.close();

        Log.d("dele teAnimal", animal.toString());

    }

}
