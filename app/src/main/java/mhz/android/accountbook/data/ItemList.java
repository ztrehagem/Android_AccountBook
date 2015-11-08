package mhz.android.accountbook.data;

import android.content.Context;

import java.util.ArrayList;

import mhz.android.accountbook.R;
import mhz.android.accountbook.adapter.ItemListAdapter;

/**
 * Created by MHz on 2015/11/07.
 */
public class ItemList {
    private ItemListAdapter adapter;

    private ArrayList<Item> viewItemList;

    ItemList(Context applicationContext) {
        adapter = new ItemListAdapter(applicationContext, R.layout.list_view_item);
    }

    public ItemListAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<Item> getViewItemList() {
        return viewItemList;
    }

    public void reloadList() {
        viewItemList = DataController.db.getItemsForListView();
        adapter.clear();
        adapter.addAll(viewItemList);
        adapter.add(new Item());
    }

    public Item getItemByViewPosition(int viewItemPosition) {
        return viewItemList.get(viewItemPosition);
    }

    public void addItem(int y, int m, int d, int genreId, String title, int amount) {
        DataController.db.addItem(y, m, d, genreId, title, amount);
    }

    public void updateItem(int itemId, int y, int m, int d, int genreId, String title, int amount) {
        DataController.db.updateItem(itemId, y, m, d, genreId, title, amount);
    }

    public void deleteItemByViewPosition(int viewItemPosition) {
        deleteItemById(getItemByViewPosition(viewItemPosition).id);
    }

    public void deleteItemById(int itemId) {
        DataController.db.deleteItem(itemId);
    }
}