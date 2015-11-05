package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        }

        return super.onOptionsItemSelected(item);
    }
}
