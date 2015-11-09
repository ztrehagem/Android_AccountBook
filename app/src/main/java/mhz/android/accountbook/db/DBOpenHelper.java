package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mhz.android.accountbook.C;

/**
 * Created by MHz on 2015/11/01.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    static final int dbVersion = 5;

    public DBOpenHelper(Context context) {
        super(context, "mydb.db", null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(C.Tag, "Database upgrade to v" + newVersion + " from v" + oldVersion);

        switch (oldVersion) {
            case 5:
                break;
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
        try {
            db.execSQL("drop table Items;");
        } catch (SQLiteException exc) {
            Log.e("AccountBook", "SQLException : drop table Items");
        }
        try {
            db.execSQL("drop table Genre;");
        } catch (SQLiteException exc) {
            Log.e("AccountBook", "SQLException : drop table Genre");
        }
        this.onCreate(db);
    }
}
