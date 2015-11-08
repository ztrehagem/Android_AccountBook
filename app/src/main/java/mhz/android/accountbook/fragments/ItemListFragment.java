package mhz.android.accountbook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
                String[] items = new String[] {"修正", "削除"};
                new AlertDialog.Builder(getContext())
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), EditItemActivity.class);
                                        intent.putExtra(C.IntentExtraName_RequestCode, C.RequestCode_ModifyItem);
                                        intent.putExtra(C.IntentExtraName_TargetItemPosition, position);
                                        startActivity(intent);
                                        break;

                                    case 1:
                                        new AlertDialog.Builder(getContext())
                                                .setMessage("この項目を削除しますか？")
                                                .setNegativeButton(R.string.dialogNegative_cancel, null)
                                                .setPositiveButton(R.string.actionName_delete, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        DataController.itemList.deleteItemByViewPosition(position);
                                                        DataController.itemList.reloadList();
                                                        DataController.sumList.reloadList();
                                                        Toast.makeText(getContext(), R.string.resultMsg_delete, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
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
