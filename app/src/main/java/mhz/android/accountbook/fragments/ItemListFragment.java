package mhz.android.accountbook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import mhz.android.accountbook.EditItemActivity;
import mhz.android.accountbook.R;
import mhz.android.accountbook.ViewDataController;

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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(getContext(), EditItemActivity.class);
                intent.putExtra("request", R.integer.requestCode_ModifyItem);
                intent.putExtra("view_item_position", position);
                startActivity(intent);
            }
        });
    }
}
