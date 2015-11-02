package mhz.android.accountbook;

import android.content.Context;

import java.util.ArrayList;

import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.Item;
import mhz.android.accountbook.list.ItemListAdapter;

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

    private static MySQLiteController db;

    //****************//

    private ViewDataController(Context applicationContext){
        db = MySQLiteController.getInstance();
        itemList = new ItemList(applicationContext);
    }

    public static ItemList itemList = null;
    public class ItemList {
        private ItemListAdapter adapter;

        private ArrayList<Item> viewItemList;

        private ItemList(Context applicationContext){
            adapter = new ItemListAdapter(applicationContext, R.layout.list_view_item);
        }
        public ItemListAdapter getAdapter(){
            return adapter;
        }
        public void reloadList() {
            adapter.clear();
            viewItemList = db.getItemsForListView();
            adapter.addAll( viewItemList );
        }
        public void deleteItem( int viewItemPosition ) {
            //** delete
        }
    }

}
