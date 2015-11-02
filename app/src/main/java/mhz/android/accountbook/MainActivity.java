package mhz.android.accountbook;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mhz.android.accountbook.db.MySQLiteController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //** initialize global data
        MySQLiteController.createInstance(getApplicationContext());

        ViewDataController.createInstance(getApplicationContext());
        ViewDataController.itemList.reloadList();

        //** view
        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));


    }

    @Override
    protected void onDestroy() {
        ViewDataController.detachInstance();
        MySQLiteController.getInstance().close();
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

        switch( item.getItemId() ) {
            case R.id.db_init:
                MySQLiteController.getInstance().dbInitialize();
                Toast.makeText(getApplicationContext(), "データベースを初期化しました", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_item:
                startActivityForResult(new Intent(getApplicationContext(), AddItemActivity.class), R.integer.requestCode_AddItem);
                break;

            case R.id.edit_genre:
                startActivityForResult(new Intent(getApplicationContext(), EditGenreActivity.class), R.integer.requestCode_EditGenre);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch ( requestCode ) {
            case R.integer.requestCode_AddItem:
                ViewDataController.itemList.reloadList();
                break;

            case R.integer.requestCode_EditGenre:
                break;
        }
    }
}
