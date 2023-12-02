package com.example.filmographyapp;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    public void displayRecords(SQLiteDatabase sqdb)
    {
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
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

    public String allRecordsInFilmsTable(SQLiteDatabase sqdb)
    {
        String result = "";
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    String id = c.getString(0);
                    result = result + id + ". ";

                    String movie_name = c.getString(1);
                    result = result + movie_name + ", ";

                    String director = c.getString(2);
                    result = result + director + ", ";

                    String release_year = c.getString(3);
                    result = result + release_year + ", ";

                    String genre = c.getString(4);
                    result = result + genre + "\n"; // new line control character

                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            }
        }
        c.close();

        return result;
    } // public String allRecordsInFilmsTable(SQLiteDatabase sqdb)

    public String searchByTitleInFilmsTable(SQLiteDatabase sqdb, String searchTitle) {
        String result = "";
        // Use LIKE operator and '%' wildcard to search for any occurrence of searchTitle in any column
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable WHERE movie_name LIKE '%" + searchTitle + "%' OR director LIKE '%" + searchTitle + "%' OR release_year LIKE '%" + searchTitle + "%' OR genre LIKE '%" + searchTitle + "%'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    result = result + id + ". ";
                    String movie_name = c.getString(1);
                    result = result + movie_name + ", ";
                    String director = c.getString(2);
                    result = result + director + ", ";
                    String release_year = c.getString(3);
                    result = result + release_year + ", ";
                    String genre = c.getString(4);
                    result = result + genre + "\n"; // new line control character
                    Log.w("FilmsTable", "ID = " + id + " movie_name = " + movie_name);
                } while (c.moveToNext());
            } else {
                result = "No Records Found for the Search title: " + searchTitle;
            }
            c.close();
        }

        return result;
    }


    public int numberOfRecordsInFilmsTable(SQLiteDatabase sqdb)
    {
        int count = 0;
        Cursor c = sqdb.rawQuery("SELECT count(*) FROM FilmsTable", null);
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

    } // public int numberOfRecordsInFilmsTable(SQLiteDatabase sqdb)

    public int numberOfRecsSearchBydirectorInFilmsTable(SQLiteDatabase sqdb, String searchdirector)
    {
        int count = 0;
        Cursor c = sqdb.rawQuery("SELECT count(*) FROM FilmsTable where director = '" + searchdirector + "';", null);
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

    } // public int numberOfRecsSearchBydirectorInFilmsTable(SQLiteDatabase sqdb)

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

    public ArrayList<String> allRecordsInFilmsTableArrayList(SQLiteDatabase sqdb)
    {
        ArrayList<String> recordList = new ArrayList<String>();

        String result = "";
        Cursor c = sqdb.rawQuery("SELECT * FROM FilmsTable", null);
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

    } // public ArrayList<String> allRecordsInFilmsTableArrayList(SQLiteDatabase sqdb)


    public void updateRecord(SQLiteDatabase sqdb,
                             String id, String Song, String director, String release_year, String genre)
    {

        //UPDATE Customers
        //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        //WHERE CustomerID = 1;

        String updateString = "UPDATE FilmsTable SET movie_name = '" + Song + "', director = '" + director + "',";
        updateString = updateString + " release_year = '" + release_year + "', genre = '" + genre + "'";
        updateString = updateString + " WHERE id = " + id + ";";

        Log.w("UPDATE","updateString = " + updateString);

        sqdb.execSQL(updateString);

    }   //  public void updateRecord(SQLiteDatabase sqdb, String id, String Song, String director, String release_year, String genre)

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
