package mhz.android.accountbook;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by MHz on 2015/11/01.
 */
public class AddItemSpinnerAdapter extends ArrayAdapter<String> {

    int resource;
    int resource_drop;
    ArrayList< Pair< Integer, Pair< String, Integer > > > list;

    public AddItemSpinnerAdapter(Context context, int resource, ArrayList< Pair< Integer, Pair< String, Integer > > > list) {
        super(context, resource);
        this.resource = android.R.layout.simple_spinner_item;
        this.resource_drop = android.R.layout.simple_spinner_dropdown_item;

        this.list = list;

        String[] strList = new String[list.size()];
        for( int i = 0; i < list.size(); i++ ) {
            strList[i] = list.get(i).second.first;
        }
        super.addAll( strList );
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource_drop, null);
        }

        this.setTextView((TextView) convertView.findViewById(android.R.id.text1), position);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }

        this.setTextView((TextView) convertView.findViewById(android.R.id.text1), position);

        return convertView;
    }

    private void setTextView( TextView textView, int position ) {
//        Pair< String, Integer > item = list.get(position).second;
//        textView.setText(item.first);
        textView.setText(super.getItem(position));
//        textView.setTextColor( item.second );
        textView.setTextColor( list.get(position).second.second );
    }
}
