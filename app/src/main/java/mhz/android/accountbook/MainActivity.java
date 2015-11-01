package mhz.android.accountbook;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

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
                /*db.addItem(2015, 11, 1, 1, "test item", 1000);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
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
}
