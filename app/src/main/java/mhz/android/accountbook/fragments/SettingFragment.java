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
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import mhz.android.accountbook.R;
import mhz.android.accountbook.data.DataController;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        String[] items = new String[]{
                getString(R.string.actionTitle_changeStartDay)
        };

        ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ViewGroup v = new FrameLayout(getContext());
                        getLayoutInflater(savedInstanceState).inflate(R.layout.view_set_start_day, v);

                        final NumberPicker numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
                        numberPicker.setMinValue(1);
                        numberPicker.setMaxValue(28);
                        numberPicker.setValue(DataController.displayMonth.getStartDay());
                        new AlertDialog.Builder(SettingFragment.this.getContext())
                                .setTitle(R.string.actionTitle_changeStartDay)
                                .setView(v)
                                .setNegativeButton(R.string.actionName_cancel, null)
                                .setPositiveButton(R.string.actionName_decision, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DataController.displayMonth.setStartDay((byte) numberPicker.getValue());
                                        DataController.itemList.reloadList();
                                        DataController.displayMonth.updateDisplayMonthText();
                                        Toast.makeText(SettingFragment.this.getContext(), R.string.resultMsg_change, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                        break;
                }
            }
        });

        return v;
    }
}