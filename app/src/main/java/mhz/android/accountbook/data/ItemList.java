package mhz.android.accountbook.data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import mhz.android.accountbook.C;
import mhz.android.accountbook.R;
import mhz.android.accountbook.adapter.ItemListAdapter;

/**
 * Created by MHz on 2015/11/07.
 */
public class ItemList {
    private ItemListAdapter adapter;

    private ArrayList<Item> viewItemList;

    ItemList(Context applicationContext) {
        viewItemList = new ArrayList<>();
        adapter = new ItemListAdapter(applicationContext, R.layout.list_view_item, viewItemList);
        reloadList();
    }

    public ItemListAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<Item> getViewItemList() {
        return viewItemList;
    }

    public void reloadList() {
        viewItemList.clear();
        viewItemList.addAll(DataController.db.getItemsForListView());
        viewItemList.add(null);
        adapter.notifyDataSetChanged();
    }

    @Nullable
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
        Item target = getItemByViewPosition(viewItemPosition);
        if (target == null) {
            Log.w(C.Tag, "ItemList#deleteItembyViewPosition : null target position");
            return;
        }
        deleteItemById(target.id);
    }

    public void deleteItemById(int itemId) {
        DataController.db.deleteItem(itemId);
    }
}