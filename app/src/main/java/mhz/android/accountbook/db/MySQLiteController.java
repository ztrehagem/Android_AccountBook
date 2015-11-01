package mhz.android.accountbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by MHz on 2015/11/01.
 */
public class MySQLiteController {

    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    public MySQLiteController(Context context) {
        openHelper = new MySQLiteOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    public void dbInitialize() {
        openHelper.onUpgrade(db, db.getVersion(), db.getVersion());
    }

    public Cursor getAll() {
        return db.query( "Items", null, null, null, null, null, null );
    }

    public void addItem( int y, int m, int d, int genreId, String title, int amount) {
        ContentValues v = new ContentValues();
        v.put( "year", y );
        v.put( "month", m );
        v.put( "day", d );
        v.put( "genre_id", genreId );
        v.put( "title", title );
        v.put( "amount", amount );
    }

    public void close() {
        db.close();
    }
}
