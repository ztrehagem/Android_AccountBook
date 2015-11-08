package mhz.android.accountbook.data;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;

import mhz.android.accountbook.R;
import mhz.android.accountbook.adapter.SumListAdapter;

/**
 * Created by MHz on 2015/11/08.
 */
public class SumList {

    private SumListAdapter adapter;
    private ArrayList<Sum> viewSumList;

    SumList(Context applicationContext) {
        adapter = new SumListAdapter(applicationContext, R.layout.list_view_sum);
    }

    public SumListAdapter getAdapter() {
        return adapter;
    }

    private ArrayList<Sum> makeSumsForListView() {
        ArrayList<Sum> sums = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();    // genreId to aryOrder
        ArrayList<Genre> genres = DataController.db.getAllGenre();
        int p = 0;
        for (Genre g : genres) {
            sums.add(new Sum(g.name, 0, Color.rgb(g.r, g.g, g.b), 0.0f));
            map.put(g.id, p++);
        }

        ArrayList<Item> items = DataController.itemList.getViewItemList();
        int max = 0;
        for (Item i : items) {
            max = Math.max(max, sums.get(map.get(i.genreId)).sum += i.amount);
        }

        for (Sum s : sums) {
            s.rate = (float) ((double) s.sum / max);
        }

        return sums;
    }

    public void reloadList() {
        viewSumList = this.makeSumsForListView();
        adapter.clear();
        adapter.addAll(viewSumList);
    }
}
