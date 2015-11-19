package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mhz.android.accountbook.C;

/**
 * Created by MHz on 2015/11/01.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    static final int dbVersion = 6;

    public DBOpenHelper(Context context) {
        super(context, "mydb.db", null, dbVersion);
    }

    // TODO: 2015/11/19 v6が安定したらv5についての記述を消す 

    @Override
    public void onCreate(SQLiteDatabase db) {
        switch (dbVersion) {
            case 5: {
                StringBuilder sql = new StringBuilder("");
                sql.append("create table Items( ");
                sql.append("id integer primary key, ");
                sql.append("year integer not null, ");
                sql.append("month integer not null, ");
                sql.append("day integer not null, ");
                sql.append("genre_id integer not null, ");
                sql.append("title text, ");
                sql.append("amount integer not null ");
                sql.append(");");
                db.execSQL(sql.toString());

                sql.setLength(0);
                sql.append("create index idx_Items on Items( year, month, day );");
                db.execSQL(sql.toString());

                sql.setLength(0);
                sql.append("create table Genre( ");
                sql.append("id integer primary key autoincrement, ");
                sql.append("name text not null, ");
                sql.append("r integer not null, ");
                sql.append("g integer not null, ");
                sql.append("b integer not null ");
                sql.append(");");
                db.execSQL(sql.toString());

                ContentValues v = new ContentValues();
                v.put("id", 1);
                v.put("name", "分類なし");
                v.put("r", 240);
                v.put("g", 100);
                v.put("b", 100);
                db.insert("Genre", null, v);
                break;
            }
            case 6: {
                db.execSQL("create table Items( " +
                        "id integer primary key, " +
                        "year integer not null, " +
                        "month integer not null, " +
                        "day integer not null, " +
                        "genre_id integer not null, " +
                        "title text, " +
                        "amount integer not null " +
                        ");");

                db.execSQL("create index idx_Items on Items( year, month, day );");

                db.execSQL("create table Genre( " +
                        "id integer primary key, " +
                        "view_order integer unique not null, " +
                        "name text not null, " +
                        "r integer not null, " +
                        "g integer not null, " +
                        "b integer not null " +
                        ");");

                ContentValues v = new ContentValues();
                v.put("id", 0);
                v.put("view_order", 0);
                v.put("name", "分類なし");
                v.put("r", 240);
                v.put("g", 100);
                v.put("b", 100);
                db.insert("Genre", null, v);
                break;
            }


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(C.Tag, "Database upgrade to v" + newVersion + " from v" + oldVersion);

        switch (oldVersion) {
            case 5: { // update to v6
                db.execSQL("drop table if exists TMP_Genre;");
                db.execSQL("create table TMP_Genre( id integer, name text, r integer, g integer, b integer );");
                db.execSQL("insert into TMP_Genre select * from Genre;");
                db.execSQL("drop table Genre;");
                db.execSQL("create table Genre( " +
                        "id integer primary key, " +
                        "view_order integer unique not null, " +
                        "name text not null, " +
                        "r integer not null, " +
                        "g integer not null, " +
                        "b integer not null " +
                        ");");
                Cursor c = db.query("TMP_Genre", null, null, null, null, null, "id asc");
                if (c.moveToFirst()) {
                    db.execSQL("begin transaction;");
                    ContentValues v = new ContentValues();
                    int vo = 0;
                    do {
                        v.clear();
                        v.put("id", c.getInt(c.getColumnIndex("id")));
                        v.put("view_order", vo++);
                        v.put("name", c.getString(c.getColumnIndex("name")));
                        v.put("r", c.getInt(c.getColumnIndex("r")));
                        v.put("g", c.getInt(c.getColumnIndex("g")));
                        v.put("b", c.getInt(c.getColumnIndex("b")));
                        db.insert("Genre", null, v);
                    } while (c.moveToNext());
                    db.execSQL("commit;");
                }
                c.close();
                db.execSQL("drop table TMP_Genre;");
                break;
            }
        }

        if (++oldVersion < newVersion)
            onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(C.Tag, "Database downgrade to v" + newVersion + " from v" + oldVersion);
        initialize(db);
    }

    public void initialize(SQLiteDatabase db) {
        db.execSQL("drop table if exists Items;");
        db.execSQL("drop table if exists Genre;");
        this.onCreate(db);
    }
}
