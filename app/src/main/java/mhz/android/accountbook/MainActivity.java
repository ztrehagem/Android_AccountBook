package mhz.android.accountbook;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mhz.android.accountbook.db.MySQLiteController;

public class MainActivity extends AppCompatActivity {

    MySQLiteController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ViewPager) findViewById(R.id.pager)).setAdapter(new MainFragmentStatePagerAdapter(getSupportFragmentManager()));

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button button_dbinit = (Button)findViewById(R.id.button_dbinit);
        button_dbinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.dbInitialize();
                Toast.makeText(getApplicationContext(), "DB Initialized", Toast.LENGTH_SHORT).show();
            }
        });

        db = new MySQLiteController(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        db.close();

        super.onDestroy();
    }
}
