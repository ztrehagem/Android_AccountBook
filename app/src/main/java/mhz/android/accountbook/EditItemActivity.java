package mhz.android.accountbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import mhz.android.accountbook.db.MySQLiteController;

public class EditItemActivity extends AppCompatActivity {

    private MySQLiteController db;

    private ArrayList< Pair< Integer, Pair< String, Integer > > > allGenreList;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("request", 0);


        //** initialize
        allGenreList = MySQLiteController.getInstance().getAllGenre();
        spinner = (Spinner)findViewById(R.id.spinner);

        //** view
        spinner.setAdapter(new EditItemSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGenreList));


        //** event listener
        switch( requestCode ) {
            case R.integer.requestCode_AddItem:
                setTitle(R.string.activity_title_addItem);
                findViewById(R.id.buttons_modify).setVisibility(View.GONE);

                Button button_add = (Button)findViewById(R.id.button_add);
                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        int day = datePicker.getDayOfMonth();

//                int genreId = 1; // default
                        int genreId = allGenreList.get(spinner.getSelectedItemPosition()).first;


                        String title = ((EditText) findViewById(R.id.input_title)).getText().toString();

                        String amount_str = ((EditText) findViewById(R.id.input_amount)).getText().toString();
                        if (amount_str.equals("")) {
                            Toast.makeText(getApplicationContext(), R.string.errorMsg_amountIsEmpty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int amount = Integer.parseInt(amount_str);

                        MySQLiteController.getInstance().addItem(year, month, day, genreId, title, amount);

                        Toast.makeText(getApplicationContext(), R.string.successMsg_ItemAdded, Toast.LENGTH_SHORT).show();
                        ViewDataController.itemList.reloadList();
                        finish();
                    }
                });
                break;

            case R.integer.requestCode_ModifyItem:
                setTitle(R.string.activity_title_modifyItem);
                findViewById(R.id.buttons_add).setVisibility(View.GONE);

                Button button_delete = (Button)findViewById(R.id.button_delete);
                button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 削除
                    }
                });

                Button button_modify = (Button)findViewById(R.id.button_modify);
                button_modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 修正
                    }
                });
                break;
        }

    }
}
