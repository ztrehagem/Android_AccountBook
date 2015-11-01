package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Pair;

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

    public void addItem( int y, int m, int d, int genreId, String title, int amount) {
        ContentValues v = new ContentValues();
        v.put("year", y);
        v.put("month", m);
        v.put("day", d);
        v.put( "genre_id", genreId );
        v.put( "title", title );
        v.put( "amount", amount );
        db.insert("Items", null, v);
    }

    public ArrayList<Item> getItemsForListView() {
        StringBuilder sql = new StringBuilder("");
        sql.append("select Items.year, Items.month, Items.day, Items.title, Items.amount, Genre.name, Genre.r, Genre.g, Genre.b ");
        sql.append("from Items ");
        sql.append("left outer join Genre on Items.genre_id = Genre.id;");
        Cursor c = db.rawQuery( sql.toString(), null );

        ArrayList<Item> list = new ArrayList<>();

        if( c.moveToFirst() ) {
            do {
                list.add( new Item(
                        c.getInt(c.getColumnIndex("year")),
                        c.getInt(c.getColumnIndex("month")),
                        c.getInt(c.getColumnIndex("day")),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("title")),
                        c.getInt(c.getColumnIndex("amount")),
                        (byte)c.getInt(c.getColumnIndex("r")),
                        (byte)c.getInt(c.getColumnIndex("g")),
                        (byte)c.getInt(c.getColumnIndex("b"))
                ));
            } while( c.moveToNext() );
        }

        c.close();

        return list;
    }

    public ArrayList< Pair< Integer, Pair< String, Integer > > > getAllGenre() {
        Cursor c = db.query("Genre", null, null, null, null, null, "id asc");

        ArrayList< Pair< Integer, Pair< String, Integer > > > list = new ArrayList<>();

        if( c.moveToFirst() ) {
            do {
                list.add( new Pair<>(
                        c.getInt(c.getColumnIndex("id")),
                        new Pair<>(
                                c.getString(c.getColumnIndex("name")),
                                Color.rgb(
                                        c.getInt(c.getColumnIndex("r")),
                                        c.getInt(c.getColumnIndex("g")),
                                        c.getInt(c.getColumnIndex("b"))
                                )
                        )
                ));
            } while( c.moveToNext() );
        }

        c.close();

        return list;
    }

    public void close() {
        db.close();
    }
}
