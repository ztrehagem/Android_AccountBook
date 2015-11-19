package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import mhz.android.accountbook.C;
import mhz.android.accountbook.data.DataController;
import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.Item;

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
        openHelper.initialize(db);
    }

    public SQLiteDatabase debugGetDB() {
        return db;
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
        Log.d(C.Tag, "DBController::addItem : " + y + "/" + m + "/" + d + " id=" + genreId + " title=" + title + " amount=" + amount);
    }

    public void addGenre(String name, int r, int g, int b) {
        db.execSQL("insert into Genre( view_order, name, r, g, b ) values( (select max( view_order ) + 1 from Genre), '" + name + "', " + r + ", " + g + ", " + b + " );");
        Log.d(C.Tag, "DBController::addGenre : name=" + name + " r=" + r + " g=" + g + " b=" + b);
    }

    public void deleteItem(int itemId) {
        db.delete("Items", "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void deleteGenre(int genreId) {
        Cursor c = db.query("Genre", new String[]{"view_order"}, "id = ?", new String[]{String.valueOf(genreId)}, null, null, null);
        c.moveToFirst();
        final int targetOrder = c.getInt(0);
        c.close();
        db.delete("Items", "genre_id = ?", new String[]{String.valueOf(genreId)});
        db.delete("Genre", "id = ?", new String[]{String.valueOf(genreId)});
        // TODO: 2015/11/19 unique制約でも更新順を考慮せず総デクリメントできる方法
        db.execSQL("update Genre set view_order = view_order - 1 where view_order > " + targetOrder + ";");
        Log.d(C.Tag, "DBController::deleteGenre : id=" + genreId);
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

    public void moveGenreOrder(final int direction, final int fromOrder) {
        final int toOrder = fromOrder + direction;

        if (toOrder == -1 || fromOrder == -1) {
            Log.e(C.Tag, "DBController::moveGenreOrder : ERROR : toOrder or fromOrder is -1");
            return;
        }

        db.execSQL("update Genre set view_order = -1 where view_order = " + fromOrder + ";");
        db.execSQL("update Genre set view_order = " + fromOrder + " where view_order = " + toOrder + ";");
        db.execSQL("update Genre set view_order = " + toOrder + " where view_order = -1;");

        Log.d(C.Tag, "DBController::moveGenreOrder : swap from[" + fromOrder + "]to[" + toOrder + "]");
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

        Log.d(C.Tag, "DBController::getItemsForListView : start");

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
                Log.d(C.Tag, "DBController::getItemsForListView : id=" + c.getInt(c.getColumnIndex("id")));
            } while (c.moveToNext());
        }

        Log.d(C.Tag, "DBController::getItemsForListView : end");

        c.close();

        return list;
    }

    public ArrayList<Genre> getAllGenre() {
        Cursor c = db.query("Genre", null, null, null, null, null, "view_order asc");

        ArrayList<Genre> list = new ArrayList<>();

        Log.d(C.Tag, "DBController::getAllGenre : start");

        if (c.moveToFirst()) {
            do {
                list.add(new Genre(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getInt(c.getColumnIndex("r")),
                        c.getInt(c.getColumnIndex("g")),
                        c.getInt(c.getColumnIndex("b"))
                ));
                Log.d(C.Tag, "DBController::getAllGenre : id=" + c.getInt(c.getColumnIndex("id")));
            } while (c.moveToNext());
        }

        Log.d(C.Tag, "DBController::getAllGenre : end");

        c.close();

        return list;
    }

    public int getGenreNum() {
        Cursor c = db.rawQuery("select count( * ) from Genre;", null);
        c.moveToFirst();
        final int result = c.getInt(0);
        c.close();
        return result;
    }

    public void close() {
        db.close();
    }
}
