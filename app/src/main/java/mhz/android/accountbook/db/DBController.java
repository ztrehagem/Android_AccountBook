package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import mhz.android.accountbook.Const;
import mhz.android.accountbook.data.DataController;
import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.Item;
import mhz.android.accountbook.data.Sum;

/**
 * Created by MHz on 2015/11/01.
 */
public class DBController {

    private DBOpenHelper openHelper;
    private SQLiteDatabase db;

    public DBController(Context context) {
        openHelper = new DBOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    public void dbInitialize() {
        openHelper.onUpgrade(db, db.getVersion(), db.getVersion());
    }

    public void addItem(int y, int m, int d, int genreId, String title, int amount) {
        ContentValues v = new ContentValues();
        v.put("year", y);
        v.put("month", m);
        v.put("day", d);
        v.put("genre_id", genreId);
        v.put("title", title);
        v.put("amount", amount);
        db.insert("Items", null, v);
    }

    public void addGenre(String name, int r, int g, int b) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("r", r);
        v.put("g", g);
        v.put("b", b);
        db.insert("Genre", null, v);
    }

    public void deleteItem(int itemId) {
        db.delete("Items", "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void deleteGenre(int genreId) {
        ContentValues v = new ContentValues();
        v.put("genre_id", 1);
        db.update("Items", v, "genre_id = ?", new String[]{String.valueOf(genreId)});
        db.delete("Genre", "id = ?", new String[]{String.valueOf(genreId)});
    }

    public void updateItem(int itemId, int y, int m, int d, int genreId, String title, int amount) {
        ContentValues v = new ContentValues();
        v.put("year", y);
        v.put("month", m);
        v.put("day", d);
        v.put("genre_id", genreId);
        v.put("title", title);
        v.put("amount", amount);
        db.update("Items", v, "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void updateGenre(int genreId, String name, int r, int g, int b) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("r", r);
        v.put("g", g);
        v.put("b", b);
        db.update("Genre", v, "id = ?", new String[]{String.valueOf(genreId)});
    }

    public ArrayList<Item> getItemsForListView() {

        Calendar start = DataController.displayMonth.getStart();
        Calendar end = DataController.displayMonth.getEnd();

        String sql = "";
        sql += "select Items.id, Items.year, Items.month, Items.day, Items.genre_id, Genre.name, Items.title, Items.amount, Genre.r, Genre.g, Genre.b ";
        sql += "from Items ";
        sql += "left outer join Genre on Items.genre_id = Genre.id ";
        sql += "where ( Items.year = " + start.get(Calendar.YEAR) + " and Items.month = " + (start.get(Calendar.MONTH) + 1) + " and Items.day >= " + start.get(Calendar.DAY_OF_MONTH) + " ) ";
        sql += "or ( Items.year = " + end.get(Calendar.YEAR) + " and Items.month = " + (end.get(Calendar.MONTH) + 1) + " and Items.day <= " + end.get(Calendar.DAY_OF_MONTH) + " ) ";
        sql += "order by Items.year asc, Items.month asc, Items.day asc ";
        sql += ";";
        Cursor c = db.rawQuery(sql, null);

        ArrayList<Item> list = new ArrayList<>();

        if (c.moveToFirst()) {
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
            } while (c.moveToNext());
        }

        c.close();

        return list;
    }

    public ArrayList<Genre> getAllGenre() {
        Cursor c = db.query("Genre", null, null, null, null, null, "id asc");

        ArrayList<Genre> list = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                list.add(new Genre(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getInt(c.getColumnIndex("r")),
                        c.getInt(c.getColumnIndex("g")),
                        c.getInt(c.getColumnIndex("b"))
                ));
            } while (c.moveToNext());
        }

        c.close();

        return list;
    }

    public ArrayList<Sum> getSumsForListView() {

        Calendar start = DataController.displayMonth.getStart();
        Calendar end = DataController.displayMonth.getEnd();

        String sql = "";
        sql += "select Genre.name, sum( Items.amount ), Genre.r, Genre.g, Genre.b ";
        sql += "from Genre ";
        sql += "left outer join Items on Items.genre_id = Genre.id ";
        sql += "where ( Items.year = " + start.get(Calendar.YEAR) + " and Items.month = " + (start.get(Calendar.MONTH) + 1) + " and Items.day >= " + start.get(Calendar.DAY_OF_MONTH) + " ) ";
        sql += "or ( Items.year = " + end.get(Calendar.YEAR) + " and Items.month = " + (end.get(Calendar.MONTH) + 1) + " and Items.day <= " + end.get(Calendar.DAY_OF_MONTH) + " ) ";
        sql += "group by Genre.id ";
        sql += ";";
        Cursor c = db.rawQuery(sql, null);

        ArrayList<Sum> list = new ArrayList<>();

        int max = 0;

        if( c.moveToFirst() ) {
            do {
                final int sum = c.getInt(1);
                max = Math.max( max, sum );
                list.add( new Sum(
                        c.getString(c.getColumnIndex("name")),
                        sum,
                        Color.rgb(
                                c.getInt(c.getColumnIndex("r")),
                                c.getInt(c.getColumnIndex("g")),
                                c.getInt(c.getColumnIndex("b"))
                        ),
                        0.0f
                ));
            } while (c.moveToNext());
        }

        c.close();

        for( Sum s : list ) {
            s.rate = (float)((double)s.sum / max);
            Log.d(Const.Tag, "s.genreName:"+s.genreName+"  s.rate:"+s.rate);
        }

        return list;
    }

    public void close() {
        db.close();
    }
}
