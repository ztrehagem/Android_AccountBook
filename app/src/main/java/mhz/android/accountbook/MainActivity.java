package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import mhz.android.accountbook.data.DataController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //** initialize global data
        DataController.createInstance(getApplicationContext());
        DataController.itemList.reloadList();

        //** view
        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));

        updateDisplayMonthText();

        findViewById(R.id.button_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataController.displayMonth.moveToPrev();
                DataController.itemList.reloadList();
                updateDisplayMonthText();
            }
        });
        findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataController.displayMonth.moveToNext();
                DataController.itemList.reloadList();
                updateDisplayMonthText();
            }
        });


    }

    @Override
    protected void onDestroy() {
        DataController.detachInstance();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        (this.getMenuInflater()).inflate(R.menu.optionsmenu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.db_init:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("データベースを初期化しますか？")
                        .setNegativeButton("キャンセル", null)
                        .setPositiveButton("初期化", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataController.dbInitialize();
                                Toast.makeText(getApplicationContext(), "データベースを初期化しました", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;

            case R.id.add_item:
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                intent.putExtra("request", R.integer.requestCode_AddItem);
                startActivity(intent);
                break;

            case R.id.edit_genre:
                startActivity(new Intent(getApplicationContext(), GenreListActivity.class));
                break;

            case R.id.set_start_day:
                ViewGroup v = new LinearLayout(MainActivity.this);
                getLayoutInflater().inflate(R.layout.view_set_start_day, v);

                final NumberPicker numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(28);
                numberPicker.setValue(DataController.displayMonth.getStartDay());
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.actionTitle_changeStartDay)
                        .setView(v)
                        .setNegativeButton(R.string.dialogNegative_cancel, null)
                        .setPositiveButton(R.string.actionName_decision, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataController.displayMonth.setStartDay((byte) numberPicker.getValue());
                                DataController.itemList.reloadList();
                                updateDisplayMonthText();
                                Toast.makeText(MainActivity.this, R.string.resultMsg_change, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateDisplayMonthText() {
        final TextView text = (TextView) findViewById(R.id.monthText);
        final Calendar start = DataController.displayMonth.getStart();
        text.setText(getString(R.string.monthText_single, start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DAY_OF_MONTH)));
    }
}
