package mhz.android.accountbook;

import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import mhz.android.accountbook.db.MySQLiteController;
import mhz.android.accountbook.list.Item;

public class MainActivity extends AppCompatActivity {

    static MySQLiteController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySQLiteController.createInstance(getApplicationContext());
        db = MySQLiteController.getInstance();

        setContentView(R.layout.activity_main);

        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));

        Button button_addItem = (Button)findViewById(R.id.button_addItem);
        button_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addItem(2015, 11, 1, 1, "test item", (int) (new Date().getTime()));
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
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

        Button button_checkItems = (Button)findViewById(R.id.button_checkItems);
        button_checkItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> list = db.getAllItems();
                Toast.makeText(getApplicationContext(), "count : "+list.size(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        db.close();

        super.onDestroy();
    }
}
