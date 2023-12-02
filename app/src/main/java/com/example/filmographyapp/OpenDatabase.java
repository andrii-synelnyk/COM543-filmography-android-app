package com.example.filmographyapp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class OpenDatabase extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "filmography.db";

    // TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE
    private static final int DATABASE_VERSION = 1;

    public OpenDatabase(Context context)
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );

    } // OpenDatabase(Context context)

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    } // public void onCreate(SQLiteDatabase db)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    } // public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    public ArrayList<String> allRecordsInFilmsTable(SQLiteDatabase sqdb) {
        ArrayList<String> recordList = new ArrayList<>();
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String record = "";
                    String id = c.getString(0);
                    record += id + ". ";
                    String movie_name = c.getString(1);
                    record += movie_name + ", ";
                    String director = c.getString(2);
                    record += director + ", ";
                    String release_year = c.getString(3);
                    record += release_year + ", ";
                    String genre = c.getString(4);
                    record += genre;
                    recordList.add(record);
                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
            c.close();
        }
        return recordList;
    }

    public ArrayList<String> searchInFilmsTable(SQLiteDatabase sqdb, String searchTitle) {
        ArrayList<String> resultList = new ArrayList<>();
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable WHERE movie_name LIKE '%" + searchTitle + "%' OR director LIKE '%" + searchTitle + "%' OR release_year LIKE '%" + searchTitle + "%' OR genre LIKE '%" + searchTitle + "%'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String record = "";
                    String id = c.getString(0);
                    record += id + ". ";
                    String movie_name = c.getString(1);
                    record += movie_name + ", ";
                    String director = c.getString(2);
                    record += director + ", ";
                    String release_year = c.getString(3);
                    record += release_year + ", ";
                    String genre = c.getString(4);
                    record += genre;
                    resultList.add(record);
                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            } else {
                resultList.add("No Records Found for the Search title: " + searchTitle);
            }
            c.close();
        }
        return resultList;
    }

    public void insertRecordFilmsTable(SQLiteDatabase sqdb,
                                      String movie_name, String director, String release_year, String genre)
    {
        // INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
        //VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');

        String insertString = "INSERT INTO FilmsTable(movie_name, director, release_year, genre) ";
        insertString = insertString + " VALUES ('" + movie_name + "','" + director + "','" + release_year + "','" + genre + "');";

        Log.w("INSERT",insertString);

        sqdb.execSQL(insertString);

    }   //  public void insertRecordFilmsTable(SQLiteDatabase sqdb,String movie_name, String director, String release_year, String genre)

    public ArrayList<String> getAllMovieNames(SQLiteDatabase sqdb) {
        ArrayList<String> moviesList = new ArrayList<>();

        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String movie_name = c.getString(1); // Assuming movie_name is at index 1
                    moviesList.add(movie_name);
                    Log.w("FilmsTable", "Movie Name = " + movie_name);
                } while (c.moveToNext());
            }
            c.close();
        }

        return moviesList;
    }

    public ArrayList<String> getAllIds(SQLiteDatabase sqdb) {
        ArrayList<String> idList = new ArrayList<>();

        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0); // Assuming movie_name is at index 1
                    idList.add(id);
                    Log.w("FilmsTable", "Id = " + id);
                } while (c.moveToNext());
            }
            c.close();
        }

        return idList;
    }

    public void deleteRecord(SQLiteDatabase sqdb, String movieName) {
        // Use prepared statements to prevent SQL injection
        String sql = "DELETE FROM FilmsTable WHERE movie_name = ?";
        SQLiteStatement statement = sqdb.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, movieName);
        statement.executeUpdateDelete();
    }

    @SuppressLint("Range")
    public ArrayList<String> getMovieDetailsById(SQLiteDatabase sqdb, String id) {
        ArrayList<String> movieDetails = new ArrayList<>();

        // SQL query to select the movie record by name
        String query = "SELECT * FROM FilmsTable WHERE id = ?";
        Cursor cursor = sqdb.rawQuery(query, new String[] { id });

        if (cursor != null && cursor.moveToFirst()) {
            // Assuming the columns are in the order: id, movie_name, director, release_year, genre
            movieDetails.add(cursor.getString(cursor.getColumnIndex("id")));          // ID
            movieDetails.add(cursor.getString(cursor.getColumnIndex("movie_name")));  // Movie Name
            movieDetails.add(cursor.getString(cursor.getColumnIndex("director")));    // Director
            movieDetails.add(cursor.getString(cursor.getColumnIndex("release_year")));// Release Year
            movieDetails.add(cursor.getString(cursor.getColumnIndex("genre")));       // Genre
            cursor.close();
        }
        return movieDetails;
    }

    public void updateMovieRecord(SQLiteDatabase sqdb, String id, String movieName, String director, String releaseYear, String genre) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("movie_name", movieName);
        contentValues.put("director", director);
        contentValues.put("release_year", releaseYear);
        contentValues.put("genre", genre);

        sqdb.update("FilmsTable", contentValues, "id = ?", new String[] { id });
    }


    public static void copyDatabase(Context context) throws IOException, IOException {
        String DB_PATH = context.getDatabasePath("filmography.db").getPath();
        File dbFile = new File(DB_PATH);

        if (!dbFile.exists()) {
            InputStream in = context.getAssets().open(DATABASE_NAME);
            OutputStream out = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
            out.close();
            in.close();
        }
    }
}   //  public class OpenDatabase extends SQLiteOpenHelper
