package mhz.android.accountbook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mhz.android.accountbook.data.Genre;

/**
 * Created by MHz on 2015/11/01.
 */
public class EditItemSpinnerAdapter extends ArrayAdapter<String> {

    int[] colorList;
    int[] idList;

    public EditItemSpinnerAdapter(Context context, int resource, ArrayList<Genre> list) {
        super(context, resource);

        colorList = new int[list.size()];
        idList = new int[list.size()];
        String[] strList = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Genre item = list.get(i);
            idList[i] = item.id;
            colorList[i] = Color.rgb(item.r, item.g, item.b);
            strList[i] = item.name;
        }
        super.addAll(strList);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        this.setTextView((TextView) convertView, position);

        return convertView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        this.setTextView((TextView) convertView, position);

        return convertView;
    }

    private void setTextView(TextView textView, int position) {
        textView.setText(super.getItem(position));
        textView.setTextColor(colorList[position]);
    }

    public int getPositionByGenreId(int genreId) {
        for (int i = 0; i < idList.length; i++) {
            if (idList[i] == genreId)
                return i;
        }
        return 0;
    }
}
