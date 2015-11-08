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

/**
 * Created by MHz on 2015/11/01.
 */
public class MainViewFragment extends Fragment {

    private View parentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main_view, container, false);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView listView = (ListView) parentView.findViewById(R.id.listView);
        listView.setAdapter(DataController.sumList.getAdapter());
    }
}
