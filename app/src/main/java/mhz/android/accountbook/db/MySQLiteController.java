package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mhz.android.accountbook.list.Item;

/**
 * Created by MHz on 2015/11/01.
 */
public class MySQLiteController {

    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    static MySQLiteController instance = null;

    static public MySQLiteController getInstance(){
        if( instance == null )
            throw new RuntimeException();
        return instance;
    }

    static public void createInstance(Context context) {
        instance = new MySQLiteController(context);
    }

    private MySQLiteController(Context context) {
        openHelper = new MySQLiteOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    public void dbInitialize() {
        openHelper.onUpgrade(db, db.getVersion(), db.getVersion());
    }

    public ArrayList<Item> getAllItems() {
        Cursor c = db.query( "Items", null, null, null, null, null, null );
        ArrayList<Item> list = new ArrayList<>();

        if( c.moveToFirst() ){
            do {
                list.add( new Item(
                        c.getInt(c.getColumnIndex("year")),
                        c.getInt(c.getColumnIndex("month")),
                        c.getInt(c.getColumnIndex("day")),
                        c.getInt(c.getColumnIndex("genre_id")),
                        c.getString(c.getColumnIndex("title")),
                        c.getInt(c.getColumnIndex("amount"))
                ) );
            }while( c.moveToNext() );
        }

        return list;
    }

    public void addItem( int y, int m, int d, int genreId, String title, int amount) {
        ContentValues v = new ContentValues();
        v.put( "year", y );
        v.put( "month", m );
        v.put( "day", d );
        v.put( "genre_id", genreId );
        v.put( "title", title );
        v.put( "amount", amount );
        db.insert( "Items", null, v );
    }

    public void close() {
        db.close();
    }
}
