package mhz.android.accountbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mhz.android.accountbook.R;
import mhz.android.accountbook.data.Item;

/**
 * Created by MHz on 2015/11/01.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

    private final int viewTypeNormal = 0;
    private final int viewTypeFinal = 1;

    private LayoutInflater inflater;

    public ItemListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ItemListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);

        if (getItemViewType(position) == viewTypeFinal) {
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }

        Item item = getItem(position);
        Context context = convertView.getContext();

        ((TextView) convertView.findViewById(R.id.listView_item_date))
                .setText(context.getString(R.string.listView_item_date, item.month, item.day));

        TextView genreNameView = (TextView) convertView.findViewById(R.id.listView_item_genreName);
        genreNameView.setText(context.getString(R.string.listView_item_genreName, item.genreName));
        genreNameView.setTextColor(item.color);

        ((TextView) convertView.findViewById(R.id.listView_item_title))
                .setText(context.getString(R.string.listView_item_title, item.title));

        ((TextView) convertView.findViewById(R.id.listView_item_amount))
                .setText(context.getString(R.string.listView_item_amount, item.amount));

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
