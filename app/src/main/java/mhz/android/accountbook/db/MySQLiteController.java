package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Pair;

import java.util.ArrayList;

import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.Item;

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
        v.put( "year", y );
        v.put( "month", m );
        v.put( "day", d );
        v.put( "genre_id", genreId );
        v.put( "title", title );
        v.put( "amount", amount );
        db.insert("Items", null, v);
    }

    public void addGenre( String name, int r, int g, int b ) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("r", r);
        v.put("g", g);
        v.put("b", b);
        db.insert("Genre", null, v);
    }

    public void deleteItem( int itemId ) {
        db.delete("Items", "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void deleteGenre( int genreId ){
        db.delete("Genre", "id = ?", new String[]{String.valueOf(genreId)});
        // ToDo 対象ジャンルに設定されていたアイテムのデフォルトジャンル化
    }

    public void updateItem( int itemId, int y, int m, int d, int genreId, String title, int amount ) {
        ContentValues v = new ContentValues();
        v.put("year", y);
        v.put("month", m);
        v.put("day", d);
        v.put( "genre_id", genreId );
        v.put( "title", title );
        v.put( "amount", amount );
        db.update("Items", v, "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void updateGenre( int genreId, String name, int r, int g, int b ){
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("r", r);
        v.put("g", g);
        v.put("b", b);
        db.update("Genre", v, "id = ?", new String[] {String.valueOf(genreId)});
    }

    public ArrayList<Item> getItemsForListView() {
        StringBuilder sql = new StringBuilder("");
        sql.append("select Items.id, Items.year, Items.month, Items.day, Items.genre_id, Genre.name, Items.title, Items.amount, Genre.r, Genre.g, Genre.b ");
        sql.append("from Items ");
        sql.append("left outer join Genre on Items.genre_id = Genre.id;");
        Cursor c = db.rawQuery(sql.toString(), null);

        ArrayList<Item> list = new ArrayList<>();

        if( c.moveToFirst() ) {
            do {
                list.add(new Item(
                        c.getInt(c.getColumnIndex("id")),
                        c.getInt(c.getColumnIndex("year")),
                        c.getInt(c.getColumnIndex("month")),
                        c.getInt(c.getColumnIndex("day")),
                        c.getInt(c.getColumnIndex("genre_id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("title")),
                        c.getInt(c.getColumnIndex("amount")),
                        Color.rgb(
                                c.getInt(c.getColumnIndex("r")),
                                c.getInt(c.getColumnIndex("g")),
                                c.getInt(c.getColumnIndex("b"))
                        )
                ));
            } while( c.moveToNext() );
        }

        c.close();

        return list;
    }

    public ArrayList<Genre> getAllGenre() {
        Cursor c = db.query("Genre", null, null, null, null, null, "id asc");

        ArrayList<Genre> list = new ArrayList<>();

        if( c.moveToFirst() ) {
            do {
                list.add( new Genre(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getInt(c.getColumnIndex("r")),
                        c.getInt(c.getColumnIndex("g")),
                        c.getInt(c.getColumnIndex("b"))
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
