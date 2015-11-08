package mhz.android.accountbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mhz.android.accountbook.R;
import mhz.android.accountbook.data.Sum;

/**
 * Created by MHz on 2015/11/08.
 */
public class SumListAdapter extends ArrayAdapter<Sum> {

    private LayoutInflater inflater;

    public SumListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_view_sum, parent, false);

        final Sum sum = getItem(position);
        final Context context = convertView.getContext();

        final TextView textView_genreName = (TextView) convertView.findViewById(R.id.listView_genre_name);
        textView_genreName.setText(sum.genreName);
        textView_genreName.setTextColor(sum.color);

        final TextView textView_sum = (TextView) convertView.findViewById(R.id.listView_sum);
        textView_sum.setText(context.getString(R.string.listView_sum, sum.sum));

        final View rateView = convertView.findViewById(R.id.rate);
        rateView.setBackgroundColor(sum.color);
        // TODO setWidth

        convertView.findViewById(R.id.wrapper).setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
