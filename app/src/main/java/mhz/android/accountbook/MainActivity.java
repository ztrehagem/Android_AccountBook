package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import mhz.android.accountbook.data.DataController;
import mhz.android.accountbook.fragments.ItemListFragment;
import mhz.android.accountbook.fragments.SettingFragment;
import mhz.android.accountbook.fragments.SumListFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-- initialize global data
        DataController.createInstance(getApplicationContext(), this);

        //-- view setting
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.button_prev).setOnClickListener(new MoveMonthButtonListener(MoveMonthButtonListener.PREV));
        findViewById(R.id.button_next).setOnClickListener(new MoveMonthButtonListener(MoveMonthButtonListener.NEXT));

        updateDisplayMonthText();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = new String[]{getString(R.string.activity_title_addItem), getString(R.string.activity_title_addGenre)};
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent;
                                switch (which) {
                                    case 0:
                                        intent = new Intent(MainActivity.this, EditItemActivity.class);
                                        intent.putExtra(C.IntentExtraName_RequestCode, C.RequestCode_AddItem);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(MainActivity.this, EditGenreActivity.class);
                                        intent.putExtra(C.IntentExtraName_RequestCode, C.RequestCode_AddGenre);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(new String[]{"データ初期化", "デバッグログ出力"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: {
                                        DataController.db.dbInitialize();
                                        Toast.makeText(MainActivity.this, "DB Initialized", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case 1: {
                                        Cursor c = DataController.db.debugGetDB().query("Genre", new String[]{"id", "view_order", "name"}, null, null, null, null, "view_order asc");
                                        if (c.moveToFirst()) {
                                            do {
                                                Log.d(C.Tag, "DEBUG//Genre : id=" + c.getInt(0) + " order=" + c.getInt(1) + " name=" + c.getString(2)
                                                );
                                            } while (c.moveToNext());
                                        }
                                        break;
                                    }
                                }
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    public void updateDisplayMonthText() {
        final TextView text = (TextView) findViewById(R.id.monthText);
        final Calendar start = DataController.displayMonth.getStart();
        final Calendar end = DataController.displayMonth.getEnd();
        text.setText(getString(R.string.monthText_single, start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DAY_OF_MONTH), end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DAY_OF_MONTH)));
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SumListFragment();
                case 1:
                    return new ItemListFragment();
                case 2:
                    return new SettingFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fragment_title_sumList);
                case 1:
                    return getString(R.string.fragment_title_itemList);
                case 2:
                    return getString(R.string.fragment_title_setting);
            }
            return null;
        }
    }

    private class MoveMonthButtonListener implements View.OnClickListener {
        static final int PREV = 0;
        static final int NEXT = 1;
        private int dir;

        MoveMonthButtonListener(int direction) {
            this.dir = direction;
        }

        @Override
        public void onClick(View v) {
            switch (dir) {
                case PREV:
                    DataController.displayMonth.moveToPrev();
                    break;
                case NEXT:
                    DataController.displayMonth.moveToNext();
                    break;
            }
            DataController.itemList.reloadList();
            DataController.sumList.reloadList();
            updateDisplayMonthText();
        }
    }
}
