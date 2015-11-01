package mhz.android.accountbook;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MHz on 2015/11/01.
 */
public class AddItemSpinnerAdapter extends ArrayAdapter<String> {

    int[] colorList;

    public AddItemSpinnerAdapter(Context context, int resource, ArrayList< Pair< Integer, Pair< String, Integer > > > list) {
        super(context, resource);

        colorList = new int[list.size()];
        String[] strList = new String[list.size()];

        for( int i = 0; i < list.size(); i++ ) {
            colorList[i] = list.get(i).second.second;
            strList[i] = list.get(i).second.first;
        }
        super.addAll(strList);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        this.setTextView((TextView) convertView, position);

        return convertView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        this.setTextView((TextView) convertView, position);

        return convertView;
    }

    private void setTextView( TextView textView, int position ) {
        textView.setText( super.getItem(position) );
        textView.setTextColor( colorList[position] );
    }
}
