package mhz.android.accountbook.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mhz.android.accountbook.R;

/**
 * Created by MHz on 2015/11/04.
 */
public class GenreListAdapter extends ArrayAdapter<Genre> {

    private final int viewTypeNormal = 0;
    private final int viewTypeFinal = 1;

    private LayoutInflater inflater;

    public GenreListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_view_genre, parent, false);

        switch (getItemViewType(position)) {
            case viewTypeNormal:
                Genre genre = getItem(position);
                Context context = convertView.getContext();

                TextView v = (TextView) convertView.findViewById(R.id.listView_genre_name);
                v.setText(context.getString(R.string.listView_genre_name, genre.name));
                v.setTextColor(Color.rgb(genre.r, genre.g, genre.b));
                break;

            case viewTypeFinal:
                convertView.setVisibility(View.INVISIBLE);
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position != getCount() - 1) ? viewTypeNormal : viewTypeFinal;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != getCount() - 1;
    }
}
