package mhz.android.accountbook.data;

/**
 * Created by MHz on 2015/11/01.
 */
public class Item {

    public int id;
    public int year;
    public int month;
    public int day;
    public int genreId;
    public String genreName;
    public String title;
    public int amount;
    public int color;

    public Item( int id, int y, int m, int d, int genreId, String genreName, String title, int amount, int color ) {
        Set( id, y, m, d, genreId, genreName, title, amount, color );
    }

    public void Set( int id, int y, int m, int d, int genreId, String genreName, String title, int amount, int color ) {
        this.id = id;
        this.year = y;
        this.month = m;
        this.day = d;
        this.genreId = genreId;
        this.genreName = genreName;
        this.title = title;
        this.amount = amount;
        this.color = color;
    }

}
