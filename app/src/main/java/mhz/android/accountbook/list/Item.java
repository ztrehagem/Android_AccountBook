package mhz.android.accountbook.list;

/**
 * Created by MHz on 2015/11/01.
 */
public class Item {

    public int year;
    public int month;
    public int day;
    public String genreName;
    public String title;
    public int amount;
    public int color;

    public Item( int y, int m, int d, String genreName, String title, int amount, int color ) {
        Set( y, m, d, genreName, title, amount, color );
    }

    public void Set( int y, int m, int d, String genreName, String title, int amount, int color ) {
        this.year = y;
        this.month = m;
        this.day = d;
        this.genreName = genreName;
        this.title = title;
        this.amount = amount;
        this.color = color;
    }

}
