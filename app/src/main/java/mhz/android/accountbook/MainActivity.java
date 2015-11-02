package mhz.android.accountbook;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.ItemListAdapter;

public class MainActivity extends AppCompatActivity {

    private final int requestCode_AddItemActivity = 1;
    private final int requestCore_EditGenreActivity = 2;

    private MySQLiteController db;
    private ItemListAdapter itemListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //** initialize global data
        MySQLiteController.createInstance(getApplicationContext());
        db = MySQLiteController.getInstance();

        ItemListAdapter.createInstance(getApplicationContext(), 0, db.getItemsForListView());
        itemListAdapter = ItemListAdapter.getInstance();

        //** view
        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));


    }

    @Override
    protected void onDestroy() {
        db.close();

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
                db.dbInitialize();
                Toast.makeText(getApplicationContext(), "データベースを初期化しました", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_item:
                startActivityForResult(new Intent(getApplicationContext(), AddItemActivity.class), requestCode_AddItemActivity);
                break;

            case R.id.edit_genre:
//                db.addGenre( "なにか", 100, 100, 255 );
                startActivityForResult(new Intent(getApplicationContext(), EditGenreActivity.class), requestCore_EditGenreActivity);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch ( requestCode ) {
            case requestCode_AddItemActivity:
                itemListAdapter.refreshList(db.getItemsForListView());
                break;

            case requestCore_EditGenreActivity:
                break;
        }
    }
}
