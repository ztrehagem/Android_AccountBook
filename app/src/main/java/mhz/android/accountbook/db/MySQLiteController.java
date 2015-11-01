package mhz.android.accountbook.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MHz on 2015/11/01.
 */
public class MySQLiteController {

    private Context context;
    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    public MySQLiteController(Context context) {
        this.context = context;
        openHelper = new MySQLiteOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    public void dbInitialize() {
        try {
            db.execSQL("drop table Items;");
        }
        catch (SQLiteException exc){
            Log.e("AccountBook", "SQLException : drop table Items");
        }
        try {
            db.execSQL("drop table Genre;");
        }
        catch (SQLiteException exc){
            Log.e("AccountBook", "SQLException : drop table Genre");
        }
        openHelper.onCreate(db);
    }

    public Cursor getAll() {
        return db.query( "Items", null, null, null, null, null, null );
    }

    public void close() {
        db.close();
    }
}
