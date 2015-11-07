package mhz.android.accountbook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import mhz.android.accountbook.R;
import mhz.android.accountbook.db.DBController;
import mhz.android.accountbook.Const;

/**
 * Created by MHz on 2015/11/02.
 */
public class DataController {

    public static DisplayMonth displayMonth = null;
    public static ItemList itemList = null;
    public static GenreList genreList = null;
    private static DataController mInstance = null;

    //****************//
    private static DBController db;

    private DataController(Context applicationContext) {
        db = new DBController(applicationContext);
        displayMonth = new DisplayMonth(applicationContext);
        itemList = new ItemList(applicationContext);
        genreList = new GenreList(applicationContext);
    }

    //****************//

    public static void dbInitialize() {
        db.dbInitialize();
    }

    public static void createInstance(Context applicationContext) {
        if (mInstance == null)
            mInstance = new DataController(applicationContext);
    }

    public static void detachInstance() {
        mInstance = null;
        db.close();
    }

    public class DisplayMonth {
        // ToDo 月開始日が29日以降でも耐えられる仕組み

        private byte startDay;
        private Calendar start, end;
        private Context applicationContext;

        private DisplayMonth(Context applicationContext) {
            this.applicationContext = applicationContext;

            setStartDay((byte) PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt(Const.SharedPreferenceKey_StartDay, 1));

            Log.d(Const.Tag, "start.m:" + (start.get(Calendar.MONTH) + 1) + " start.d:" + start.get(Calendar.DAY_OF_MONTH));
            Log.d(Const.Tag, "  end.m:" + (end.get(Calendar.MONTH) + 1) + "   end.d:" + end.get(Calendar.DAY_OF_MONTH));
        }

        public void moveToNext() {
            start.add(Calendar.MONTH, 1);
            end.add(Calendar.MONTH, 1);
        }

        public void moveToPrev() {
            start.add(Calendar.MONTH, -1);
            end.add(Calendar.MONTH, -1);
        }

        public Calendar getStart() {
            return start;
        }

        public Calendar getEnd() {
            return end;
        }

        public int getStartDay() {
            return startDay;
        }

        public void setStartDay(byte startDay) {
            this.startDay = startDay;
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit();
            editor.putInt(Const.SharedPreferenceKey_StartDay, startDay);
            editor.apply();

            start = Calendar.getInstance();
            if (start.get(Calendar.DAY_OF_MONTH) < startDay)
                start.add(Calendar.MONTH, -1);
            start.set(Calendar.DAY_OF_MONTH, startDay);
            end = Calendar.getInstance();
            end.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
            end.add(Calendar.DAY_OF_MONTH, -1);
            end.add(Calendar.MONTH, 1);
        }
    }

    public class ItemList {
        private ItemListAdapter adapter;

        private ArrayList<Item> viewItemList;

        private ItemList(Context applicationContext) {
            adapter = new ItemListAdapter(applicationContext, R.layout.list_view_item);
        }

        public ItemListAdapter getAdapter() {
            return adapter;
        }

        public void reloadList() {
            viewItemList = db.getItemsForListView();
            viewItemList.add(new Item());
            adapter.clear();
            adapter.addAll(viewItemList);
        }

        public Item getItemByViewPosition(int viewItemPosition) {
            return viewItemList.get(viewItemPosition);
        }

        public void addItem(int y, int m, int d, int genreId, String title, int amount) {
            db.addItem(y, m, d, genreId, title, amount);
        }

        public void updateItem(int itemId, int y, int m, int d, int genreId, String title, int amount) {
            db.updateItem(itemId, y, m, d, genreId, title, amount);
        }

        public void deleteItemByViewPosition(int viewItemPosition) {
            deleteItemById(getItemByViewPosition(viewItemPosition).id);
        }

        public void deleteItemById(int itemId) {
            db.deleteItem(itemId);
        }
    }

    public class GenreList {

        private GenreListAdapter adapter = null;
        private ArrayList<Genre> viewGenreList;
        private Context context;

        private GenreList(Context context) {
            this.context = context;
        }

        public void createListAdapter() {
            if (adapter == null)
                adapter = new GenreListAdapter(context, R.layout.list_view_genre);
        }

        public void detachListAdapter() {
            adapter = null;
        }

        public GenreListAdapter getAdapter() {
            return adapter;
        }

        public void reloadList() {
            viewGenreList = db.getAllGenre();
            viewGenreList.add(new Genre());
            adapter.clear();
            adapter.addAll(viewGenreList);
        }

        public ArrayList<Genre> getAllGenre() {
            return db.getAllGenre();
        }

        public Genre getGenreByViewPosition(int viewPosition) {
            return viewGenreList.get(viewPosition);
        }

        public void addGenre(String genreName, int r, int g, int b) {
            db.addGenre(genreName, r, g, b);
        }

        public void addGenre(String genreName, int color) {
            this.addGenre(genreName, Color.red(color), Color.green(color), Color.blue(color));
        }

        public void updateGenre(int genreId, String name, int r, int g, int b) {
            db.updateGenre(genreId, name, r, g, b);
        }

        public void updateGenre(int genreId, String name, int color) {
            this.updateGenre(genreId, name, Color.red(color), Color.green(color), Color.blue(color));
        }

        public void deleteGenreById(int genreId) {
            db.deleteGenre(genreId);
        }
    }

}
