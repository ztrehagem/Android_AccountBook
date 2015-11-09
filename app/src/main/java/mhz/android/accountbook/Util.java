package mhz.android.accountbook;

/**
 * Created by MHz on 2015/11/09.
 */
public class Util {

    public static String makeMoneyFormat(int money) {
        StringBuilder str = new StringBuilder("");

        StringBuilder target = new StringBuilder(String.valueOf(money % 1000));

        while ((money /= 1000) > 0) {
            while (target.length() < 3)
                target.insert(0, "0");
            str.insert(0, target);
            str.insert(0, ",");
            target.setLength(0);
            target.append(String.valueOf(money % 1000));
        }

        str.insert(0, target);

        return str.toString();
    }
}
