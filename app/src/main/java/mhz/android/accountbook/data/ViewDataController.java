package mhz.android.accountbook.data;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;

import mhz.android.accountbook.R;
import mhz.android.accountbook.db.MySQLiteController;

/**
 * Created by MHz on 2015/11/02.
 */
public class ViewDataController {

    private static ViewDataController mInstance = null;
    public static void createInstance(Context applicationContext){
        if( mInstance == null )
            mInstance = new ViewDataController(applicationContext);
    }
    public static void detachInstance(){
        mInstance = null;
    }

    //****************//

    private MySQLiteController db;

    //****************//

    private ViewDataController(Context applicationContext){
        db = MySQLiteController.getInstance();
        itemList = new ItemList(applicationContext);
        genreList = new GenreList(applicationContext);
    }

    public static ItemList itemList = null;
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
            viewItemList.add( new Item() );
            adapter.clear();
            adapter.addAll(viewItemList);
        }

        public Item getItemByViewPosition(int viewItemPosition) {
            return viewItemList.get(viewItemPosition);
        }

        public void addItem(int y, int m, int d, int genreId, String title, int amount) {
            db.addItem(y, m, d, genreId, title, amount);
        }

        public void updateItem( int itemId, int y, int m, int d, int genreId, String title, int amount ) {
            db.updateItem( itemId, y, m, d, genreId, title, amount );
        }

        public void deleteItemByViewPosition(int viewItemPosition) {
            deleteItemById(getItemByViewPosition(viewItemPosition).id);
        }

        public void deleteItemById(int itemId) {
            db.deleteItem(itemId);
        }
    }

    public static GenreList genreList = null;
    public class GenreList {

        private GenreListAdapter adapter = null;
        private ArrayList<Genre> viewGenreList;
        private Context context;

        private GenreList(Context context) {
            this.context = context;
        }
        public void createListAdapter() {
            if( adapter == null )
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
        public Genre getGenreByViewPosition( int viewPosition ) {
            return viewGenreList.get(viewPosition);
        }
        public void addGenre( String genreName, int r, int g, int b ){
            db.addGenre(genreName, r, g, b);
        }
        public void addGenre( String genreName, int color ){
            this.addGenre(genreName, Color.red(color), Color.green(color), Color.blue(color));
        }
        public void updateGenre( int genreId, String name, int r, int g, int b ){
            db.updateGenre(genreId, name, r, g, b);
        }
        public void updateGenre( int genreId, String name, int color ){
            this.updateGenre(genreId, name, Color.red(color), Color.green(color), Color.blue(color));
        }
        public void deleteGenreById( int genreId ) {
            db.deleteGenre(genreId);
        }
    }

}
