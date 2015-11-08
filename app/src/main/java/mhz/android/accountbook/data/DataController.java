package mhz.android.accountbook.data;

import android.content.Context;

import mhz.android.accountbook.db.DBController;

/**
 * Created by MHz on 2015/11/02.
 */
public class DataController {

    public static DBController db;
    public static DisplayMonth displayMonth = null;
    public static ItemList itemList = null;
    public static GenreList genreList = null;
    public static SumList sumList = null;
    private static DataController mInstance = null;

    private DataController(Context applicationContext) {
        db = new DBController(applicationContext);
        displayMonth = new DisplayMonth(applicationContext);
        itemList = new ItemList(applicationContext);
        genreList = new GenreList(applicationContext);
        sumList = new SumList(applicationContext);
    }

    //****************//

    public static void createInstance(Context applicationContext) {
        if (mInstance == null)
            mInstance = new DataController(applicationContext);
    }

    public static void detachInstance() {
        mInstance = null;
        db.close();
    }
}
