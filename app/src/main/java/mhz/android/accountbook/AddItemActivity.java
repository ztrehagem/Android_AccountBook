package mhz.android.accountbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mhz.android.accountbook.db.MySQLiteController;

public class AddItemActivity extends AppCompatActivity {

    private MySQLiteController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //** initialize
        db = MySQLiteController.getInstance();

        //** view
        setContentView(R.layout.activity_add_item);

        ((Spinner)findViewById(R.id.spinner)).setAdapter( new AddItemSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, db.getAllGenre()));


        //** event listener
        Button button = (Button)findViewById(R.id.button_addItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();

                int genreId = 1; // default

                String title = ((EditText)findViewById(R.id.input_title)).getText().toString();

                String amount_str = ((EditText)findViewById(R.id.input_amount)).getText().toString();
                if( amount_str.equals("") ){
                    Toast.makeText(getApplicationContext(), R.string.errorMsg_amountIsEmpty, Toast.LENGTH_SHORT).show();
                    return;
                }
                int amount = Integer.parseInt(amount_str);

                MySQLiteController.getInstance().addItem( year, month, day, genreId, title, amount );

                Toast.makeText(getApplicationContext(), R.string.successMsg_ItemAdded, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
