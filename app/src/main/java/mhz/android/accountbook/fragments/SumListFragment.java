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
import mhz.android.accountbook.EditGenreActivity;
import mhz.android.accountbook.R;
import mhz.android.accountbook.data.DataController;

public class SumListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sum_list, container, false);
        final ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(DataController.sumList.getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] items;
                if (DataController.db.getGenreNum() == 1) {
                    items = new String[]{"分類の修正", "上へ", "下へ"};
                } else {
                    items = new String[]{"分類の修正", "分類を削除", "上へ", "下へ"};
                }
                final boolean isDeletable = items.length == 4;
                new AlertDialog.Builder(getContext())
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which >= 1 && !isDeletable)
                                    which += 1;

                                Intent intent;
                                switch (which) {
                                    case 0:
                                        intent = new Intent(getContext(), EditGenreActivity.class);
                                        intent.putExtra(C.IntentExtraName_RequestCode, C.RequestCode_ModifyGenre);
                                        intent.putExtra(C.IntentExtraName_TargetItemPosition, position);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        new AlertDialog.Builder(getContext())
                                                .setMessage(R.string.dialogMsg_deleteGenre)
                                                .setNegativeButton(R.string.actionName_cancel, null)
                                                .setPositiveButton(R.string.actionName_delete, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        DataController.sumList.deleteGenreByViewPosition(position);
                                                        DataController.itemList.reloadList();
                                                        DataController.sumList.reloadList();
                                                        Toast.makeText(getContext(), R.string.resultMsg_delete, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .show();
                                }
                            }
                        })
                        .show();
            }
        });
        return v;
    }
}