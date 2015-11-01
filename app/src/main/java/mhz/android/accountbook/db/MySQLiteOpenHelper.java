package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MHz on 2015/11/01.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final int dbVersion = 1;

    public MySQLiteOpenHelper(Context context) {
        super(context, "mydb.db", null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "create table Items( ";
        sql += "id integer primary key autoincrement, ";
        sql += "year integer not null, ";
        sql += "month integer not null, ";
        sql += "day integer not null, ";
        sql += "genre_id integer not null, ";
        sql += "amount integer not null );";
        db.execSQL(sql);

        sql = "";
        sql += "create table Genre( ";
        sql += "id integer primary key autoincrement, ";
        sql += "name varchar(50) not null, ";
        sql += "r integer not null, ";
        sql += "g integer not null, ";
        sql += "b integer not null );";
        db.execSQL(sql);

        ContentValues v = new ContentValues();
        v.put( "id", 1 );
        v.put( "name", "分類なし" );
        v.put( "r", 240 );
        v.put( "g", 100 );
        v.put( "b", 100 );
        db.insert( "Genre", null, v );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
