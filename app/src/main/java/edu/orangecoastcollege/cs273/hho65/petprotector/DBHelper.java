package edu.orangecoastcollege.cs273.hho65.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Huy Ho on 11/5/2016.
 */

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAIL = "detail";
    private static final String FIELD_IMAGE_URI = "image_name";
    private static final String FIELD_PHONE_NUMBER = "phone_number";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // TODO:  Define the SQL statement to create a new table with the fields above.
        // TODO:  Primary key should auto increment
        // TODO:  Exceute the SQL
        String table = " CREATE TABLE " + DATABASE_TABLE + " ( "
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAIL + " TEXT, "
                + FIELD_PHONE_NUMBER + " TEXT, "
                + FIELD_IMAGE_URI + " TEXT" + ")";

        database.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        // TODO:  Execute the SQL statment to drop the table
        // TODO:  Recreate the database
        database.execSQL(" DROP TABLE IF EXISTS" + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public void addPet(Pet pet) {

        // TODO:  Write the code to add a new pet to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(FIELD_NAME, pet.getmName());
        value.put(FIELD_DETAIL, pet.getmDetails());
        value.put(FIELD_PHONE_NUMBER, pet.getmPhone());
        value.put(FIELD_IMAGE_URI, String.valueOf(pet.getmImageURI()));
        db.insert(DATABASE_TABLE, null, value);
        db.close();
    }

    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petList = new ArrayList<>();

        // TODO:  Write the code to get all pets from the database and return in ArrayList
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                petList.add(new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));

            } while (cursor.moveToNext());
        }
        db.close();
        return petList;
    }

    public void deleteAllPets() {
        // TODO:  Write the code to delete all pets from the database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    public void updatePet(Pet pet) {

        // TODO:  Write the code to update a pet in the database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(FIELD_NAME, pet.getmName());
        value.put(FIELD_DETAIL, pet.getmDetails());
        value.put(FIELD_PHONE_NUMBER, pet.getmPhone());
        value.put(FIELD_IMAGE_URI, String.valueOf(pet.getmImageURI()));


        db.update(DATABASE_TABLE, value, KEY_FIELD_ID + "=?", new String[]{String.valueOf(pet.getmId())});

        db.close();


    }

    public Pet getPet(int id) {

        // TODO:  Write the code to get a specific pet from the database
        // TODO:  Replace the return null statement below with the pet from the database.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, null, KEY_FIELD_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Pet game = new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));

        db.close();
        return game;
    }
}