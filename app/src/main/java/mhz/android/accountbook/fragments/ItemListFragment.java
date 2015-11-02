package mhz.android.accountbook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setItems(R.array.itemList_selectMenu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch( which ){
                                    case 0:
                                        //** AddItemActivityを再利用するIntent
                                        break;

                                    case 1:
                                        new AlertDialog.Builder(getActivity())
                                                .setMessage(R.string.dialogMsg_deleteItem)
                                                .setPositiveButton(R.string.dialogPositive_delete, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ViewDataController.itemList.deleteItem( position );
                                                        Toast.makeText(getContext(), R.string.receiptMsg_deleteItem, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setNegativeButton(R.string.dialogNegative_cancel, null)
                                                .show();
                                        break;
                                }

                            }
                        })
                        .show();
            }
        });
    }
}
