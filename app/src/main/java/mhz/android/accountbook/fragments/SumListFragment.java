package mhz.android.accountbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mhz.android.accountbook.R;
import mhz.android.accountbook.data.DataController;

public class SumListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sum_list, container, false);
        final ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(DataController.sumList.getAdapter());
        return v;
    }
}