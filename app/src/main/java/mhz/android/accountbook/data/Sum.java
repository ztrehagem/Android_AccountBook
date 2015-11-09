package mhz.android.accountbook.data;

import android.graphics.Color;

/**
 * Created by MHz on 2015/11/08.
 */
public class Sum {

    public int genreId;
    public String genreName;
    public int sum;
    public int color;
    public float rate;

    public Sum() {
        Set(-1, null, 0, Color.rgb(0, 0, 0), 0.0f);
    }

    public Sum(int genreId, String genreName, int sum, int color, float rate) {
        Set(genreId, genreName, sum, color, rate);
    }

    public void Set(int genreId, String genreName, int sum, int color, float percentage) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.sum = sum;
        this.color = color;
        this.rate = percentage;
    }

}
