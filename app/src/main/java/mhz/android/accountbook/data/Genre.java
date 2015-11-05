package mhz.android.accountbook.data;

/**
 * Created by MHz on 2015/11/04.
 */
public class Genre {

    public int id;
    public String name;
    public int r;
    public int g;
    public int b;

    public Genre() {
        Set(-1, null, 0, 0, 0);
    }

    public Genre(int id, String name, int r, int g, int b) {
        Set(id, name, r, g, b);
    }

    public void Set(int id, String name, int r, int g, int b) {
        this.id = id;
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
