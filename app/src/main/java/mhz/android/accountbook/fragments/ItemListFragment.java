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

import mhz.android.accountbook.C;
import mhz.android.accountbook.EditItemActivity;
import mhz.android.accountbook.R;
import mhz.android.accountbook.data.DataController;

/**
 * Created by MHz on 2015/11/01.
 */
public class ItemListFragment extends Fragment {

    private View parentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_item_list, container, false);
        return parentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView listView = (ListView) parentView.findViewById(R.id.listView);
        listView.setAdapter(DataController.itemList.getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(getContext(), EditItemActivity.class);
                intent.putExtra(C.IntentExtraName_RequestCode, C.RequestCode_ModifyItem);
                intent.putExtra(C.IntentExtraName_TargetItemPosition, position);
                startActivity(intent);
            }
        });
    }
}
