package com.example.filmographyapp;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class OpenDatabase extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "filmography.db";

    // TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE
    private static final int DATABASE_VERSION = 1;

    OpenDatabase(Context context)
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

    public void displayRecords(SQLiteDatabase sqdb)
    {
        Cursor c = sqdb.rawQuery("SELECT * FROM songtable", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    String movie_name = c.getString(1);
                    String director = c.getString(2);
                    String release_year = c.getString(3);
                    String genre = c.getString(4);
                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
        }
        c.close();

    } // public void displayRecords()

    public String allRecordsInSongtable(SQLiteDatabase sqdb)
    {
        String result = "";
        Cursor c = sqdb.rawQuery("SELECT * FROM songtable", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    result = result + id + ",";

                    String movie_name = c.getString(1);
                    result = result + movie_name + ",";

                    String director = c.getString(2);
                    result = result + director + ",";

                    String release_year = c.getString(3);
                    result = result + release_year + ",";

                    String genre = c.getString(4);
                    result = result + genre + "\n"; // new line control character

                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
        }
        c.close();

        return result;
    } // public String allRecordsInSongtable(SQLiteDatabase sqdb)

    public String searchBydirectorInSongtable(SQLiteDatabase sqdb, String searchdirector)
    {
        String result = "";
        Cursor c = sqdb.rawQuery("SELECT * FROM songtable where director = '" + searchdirector + "'",
                null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    result = result + id + ",";
                    String movie_name = c.getString(1);
                    result = result + movie_name + ",";
                    String director = c.getString(2);
                    result = result + director + ",";
                    String release_year = c.getString(3);
                    result = result + release_year + ",";
                    String genre = c.getString(4);
                    result = result + genre + "\n"; // new line control character
                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
            else
            {
                result = "No Records Found for the Search director = " + searchdirector;
            }
        }
        c.close();

        return result;

    } // public String searchBydirectorInSongtable(SQLiteDatabase sqdb, String searchdirector)


    public int numberOfRecordsInSongtable(SQLiteDatabase sqdb)
    {
        int count = 0;
        Cursor c = sqdb.rawQuery("SELECT count(*) FROM songtable", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    count = Integer.parseInt( id );
                } while (c.moveToNext());
            }
        }
        c.close();

        return count;

    } // public int numberOfRecordsInSongtable(SQLiteDatabase sqdb)

    public int numberOfRecsSearchBydirectorInSongtable(SQLiteDatabase sqdb, String searchdirector)
    {
        int count = 0;
        Cursor c = sqdb.rawQuery("SELECT count(*) FROM songtable where director = '" + searchdirector + "';", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    count = Integer.parseInt( id );
                } while (c.moveToNext());
            }
        }
        c.close();

        return count;

    } // public int numberOfRecsSearchBydirectorInSongtable(SQLiteDatabase sqdb)

    public void insertRecordSongTable(SQLiteDatabase sqdb,
                                      String movie_name, String director, String release_year, String genre)
    {
        // INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
        //VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');

        String insertString = "INSERT INTO SongTable(movie_name, director, release_year, genre) ";
        insertString = insertString + " VALUES ('" + movie_name + "','" + director + "','" + release_year + "','" + genre + "');";

        Log.w("INSERT",insertString);

        sqdb.execSQL(insertString);

    }   //  public void insertRecordSongTable(SQLiteDatabase sqdb,String movie_name, String director, String release_year, String genre)

    public ArrayList<String> allRecordsInSongtableArrayList(SQLiteDatabase sqdb)
    {
        ArrayList<String> recordList = new ArrayList<String>();

        String result = "";
        Cursor c = sqdb.rawQuery("SELECT * FROM songtable", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    result = "";        // initialise result string !!!

                    String id = c.getString(0);
                    result = result + id + ",";

                    String movie_name = c.getString(1);
                    result = result + movie_name + ",";

                    String director = c.getString(2);
                    result = result + director + ",";

                    String release_year = c.getString(3);
                    result = result + release_year + ",";

                    String genre = c.getString(4);
                    result = result + genre;        // new line control character is not needed with arrayList!!

                    recordList.add( result );   // add current record string to arraylist of strings!

                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
        }
        c.close();

        return recordList;

    } // public ArrayList<String> allRecordsInSongtableArrayList(SQLiteDatabase sqdb)


    public void updateRecord(SQLiteDatabase sqdb,
                             String id, String Song, String director, String release_year, String genre)
    {

        //UPDATE Customers
        //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        //WHERE CustomerID = 1;

        String updateString = "UPDATE SongTable SET movie_name = '" + Song + "', director = '" + director + "',";
        updateString = updateString + " release_year = '" + release_year + "', genre = '" + genre + "'";
        updateString = updateString + " WHERE id = " + id + ";";

        Log.w("UPDATE","updateString = " + updateString);

        sqdb.execSQL(updateString);

    }   //  public void updateRecord(SQLiteDatabase sqdb, String id, String Song, String director, String release_year, String genre)

}   //  public class OpenDatabase extends SQLiteOpenHelper
