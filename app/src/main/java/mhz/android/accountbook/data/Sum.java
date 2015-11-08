package mhz.android.accountbook.data;

import android.graphics.Color;

/**
 * Created by MHz on 2015/11/08.
 */
public class Sum {

    public String genreName;
    public int sum;
    public int color;
    public float rate;

    public Sum() {
        Set(null, 0, Color.rgb(0, 0, 0), 0.0f);
    }

    public Sum(String genreName, int sum, int color, float rate) {
        Set(genreName, sum, color, rate);
    }

    public void Set(String genreName, int sum, int color, float percentage) {
        this.genreName = genreName;
        this.sum = sum;
        this.color = color;
        this.rate = percentage;
    }

}
