package mhz.android.accountbook.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import mhz.android.accountbook.R;

/**
 * Created by MHz on 2015/11/04.
 */
public class GenreListAdapter extends ArrayAdapter<Genre> {

    private LayoutInflater inflater;
    private int resource;

    public GenreListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Genre genre = getItem(position);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_view_genre, parent, false);
        Context context = convertView.getContext();

        TextView v = (TextView)convertView.findViewById(R.id.listView_genre_name);
        v.setText(context.getString(R.string.listView_genre_name, genre.name));
        v.setTextColor(Color.rgb(genre.r, genre.g, genre.b));

        return convertView;
    }
}
