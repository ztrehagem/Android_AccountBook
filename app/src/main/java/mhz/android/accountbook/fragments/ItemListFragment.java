package mhz.android.accountbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mhz.android.accountbook.R;
import mhz.android.accountbook.ViewDataController;
import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.Item;
import mhz.android.accountbook.list.ItemListAdapter;

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

        ListView listView = (ListView)parentView.findViewById(R.id.listView);
        listView.setAdapter(ViewDataController.itemList.getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "position : "+position+"\nid : "+id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
