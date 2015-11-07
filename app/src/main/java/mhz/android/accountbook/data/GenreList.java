package mhz.android.accountbook.data;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;

import mhz.android.accountbook.R;
import mhz.android.accountbook.adapter.GenreListAdapter;

/**
 * Created by MHz on 2015/11/07.
 */
public class GenreList {

    private GenreListAdapter adapter = null;
    private ArrayList<Genre> viewGenreList;
    private Context context;

    GenreList(Context context) {
        this.context = context;
    }

    public void createListAdapter() {
        if (adapter == null)
            adapter = new GenreListAdapter(context, R.layout.list_view_genre);
    }

    public void detachListAdapter() {
        adapter = null;
    }

    public GenreListAdapter getAdapter() {
        return adapter;
    }

    public void reloadList() {
        viewGenreList = DataController.db.getAllGenre();
        adapter.clear();
        adapter.addAll(viewGenreList);
        adapter.add(new Genre());
    }

    public ArrayList<Genre> getAllGenre() {
        return DataController.db.getAllGenre();
    }

    public Genre getGenreByViewPosition(int viewPosition) {
        return viewGenreList.get(viewPosition);
    }

    public void addGenre(String genreName, int r, int g, int b) {
        DataController.db.addGenre(genreName, r, g, b);
    }

    public void addGenre(String genreName, int color) {
        this.addGenre(genreName, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void updateGenre(int genreId, String name, int r, int g, int b) {
        DataController.db.updateGenre(genreId, name, r, g, b);
    }

    public void updateGenre(int genreId, String name, int color) {
        this.updateGenre(genreId, name, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void deleteGenreById(int genreId) {
        DataController.db.deleteGenre(genreId);
    }
}
