package mhz.android.accountbook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import mhz.android.accountbook.C;

/**
 * Created by MHz on 2015/11/07.
 */
public class DisplayMonth {
    // ToDo 月開始日が29日以降でも耐えられる仕組み

    private byte startDay;
    private Calendar start, end;
    private Context applicationContext;

    DisplayMonth(Context applicationContext) {
        this.applicationContext = applicationContext;

        setStartDay((byte) PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt(C.SharedPreferenceKey_StartDay, 1));

        Log.d(C.Tag, "start.m:" + (start.get(Calendar.MONTH) + 1) + " start.d:" + start.get(Calendar.DAY_OF_MONTH));
        Log.d(C.Tag, "  end.m:" + (end.get(Calendar.MONTH) + 1) + "   end.d:" + end.get(Calendar.DAY_OF_MONTH));
    }

    public void moveToNext() {
        start.add(Calendar.MONTH, 1);
        end.add(Calendar.MONTH, 1);
    }

    public void moveToPrev() {
        start.add(Calendar.MONTH, -1);
        end.add(Calendar.MONTH, -1);
    }

    public Calendar getStart() {
        return start;
    }

    public Calendar getEnd() {
        return end;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(byte startDay) {
        this.startDay = startDay;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit();
        editor.putInt(C.SharedPreferenceKey_StartDay, startDay);
        editor.apply();

        start = Calendar.getInstance();
        if (start.get(Calendar.DAY_OF_MONTH) < startDay)
            start.add(Calendar.MONTH, -1);
        start.set(Calendar.DAY_OF_MONTH, startDay);
        end = Calendar.getInstance();
        end.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
        end.add(Calendar.DAY_OF_MONTH, -1);
        end.add(Calendar.MONTH, 1);
    }
}