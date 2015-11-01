package mhz.android.accountbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.Item;
import mhz.android.accountbook.list.ListAdapter;

/**
 * Created by MHz on 2015/11/01.
 */
public class ItemListFragment extends Fragment {

    View parentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate( R.layout.fragment_item_list, null );
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MySQLiteController db = MySQLiteController.getInstance();
        ArrayList<Item> list = db.getAllItems();

        ListAdapter adapter = new ListAdapter(getContext(), 0, list);
        ((ListView)parentView.findViewById(R.id.listView)).setAdapter(adapter);
    }
}
