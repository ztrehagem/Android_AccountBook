package mhz.android.accountbook.data;

import android.content.Context;

import java.util.ArrayList;

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

    public void reloadList(){
        viewSumList = DataController.db.getSumsForListView();
        adapter.clear();
        adapter.addAll(viewSumList);
    }
}
