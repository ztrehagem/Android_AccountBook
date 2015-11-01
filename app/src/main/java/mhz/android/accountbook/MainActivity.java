package mhz.android.accountbook;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.ItemListAdapter;

public class MainActivity extends AppCompatActivity {

    private final int requestCode_AddItemActivity = 1;

    private MySQLiteController db;
    private ItemListAdapter itemListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //** initialize global data
        MySQLiteController.createInstance(getApplicationContext());
        db = MySQLiteController.getInstance();

        ItemListAdapter.createInstance(getApplicationContext(), 0, db.getItemsForListView());
        itemListAdapter = ItemListAdapter.getInstance();

        //** view
        setContentView(R.layout.activity_main);
        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));


        //** event listener
        Button button_addItem = (Button)findViewById(R.id.button_addItem);
        button_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(getApplicationContext(), AddItemActivity.class ), requestCode_AddItemActivity);
            }
        });

        Button button_dbInit = (Button)findViewById(R.id.button_dbInit);
        button_dbInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.dbInitialize();
                Toast.makeText(getApplicationContext(), "DB Initialized", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        db.close();

        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch ( requestCode ) {
            case requestCode_AddItemActivity:
                itemListAdapter.refreshList(db.getItemsForListView());
                break;
        }
    }
}
