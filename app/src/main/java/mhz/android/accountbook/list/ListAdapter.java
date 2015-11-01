package mhz.android.accountbook.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mhz.android.accountbook.R;

/**
 * Created by MHz on 2015/11/01.
 */
public class ListAdapter extends ArrayAdapter<Item> {

    private LayoutInflater inflater;

    public ListAdapter(Context context, int resource, List<Item> objects ) {
        super(context, resource, objects);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        Item item = (Item)getItem(position);

        if( convertView == null ){
            convertView = inflater.inflate(R.layout.list_view_item, null);
        }

        Context context = convertView.getContext();

        ((TextView)convertView.findViewById(R.id.listView_item_date))
                .setText(context.getString(R.string.listView_item_date, item.month, item.day));
        ((TextView)convertView.findViewById(R.id.listView_item_genreName))
                .setText(context.getString(R.string.listView_item_genreName, item.genreId));
        ((TextView)convertView.findViewById(R.id.listView_item_title))
                .setText(context.getString(R.string.listView_item_title, item.title));
        ((TextView)convertView.findViewById(R.id.listView_item_amount))
                .setText(context.getString(R.string.listView_item_amount, item.amount));

        return convertView;
    }
}
