package mhz.android.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mhz.android.accountbook.adapter.GenreSpinnerAdapter;
import mhz.android.accountbook.data.DataController;
import mhz.android.accountbook.data.Item;

public class EditItemActivity extends AppCompatActivity {

    private Spinner spinner;
    private DatePicker datePicker;
    private EditText editText_title;
    private EditText editText_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        final Intent intent = getIntent();
        final int requestCode = intent.getIntExtra(C.IntentExtraName_RequestCode, 0);


        //** initialize
        spinner = (Spinner) findViewById(R.id.spinner);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        editText_title = (EditText) findViewById(R.id.input_title);
        editText_amount = (EditText) findViewById(R.id.input_amount);


        //** view
        spinner.setAdapter(new GenreSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, DataController.genreList.getAllGenre()));
        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Button button_do = (Button) findViewById(R.id.button_do);


        switch (requestCode) {
            case C.RequestCode_AddItem:
                setTitle(R.string.activity_title_addItem);

                button_do.setText(getString(R.string.actionName_add));
                button_do.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CoreItemData item = makeCoreItemData();
                        if (item == null) {
                            return;
                        }
                        DataController.itemList.addItem(item.year, item.month, item.day, item.genreId, item.title, item.amount);
                        DataController.itemList.reloadList();
                        DataController.sumList.reloadList();
                        Toast.makeText(getApplicationContext(), R.string.resultMsg_add, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

            case C.RequestCode_ModifyItem:
                setTitle(R.string.activity_title_modifyItem);

                final int targetItemPosition = intent.getIntExtra(C.IntentExtraName_TargetItemPosition, -1);
                if (targetItemPosition == -1)
                    throw new RuntimeException();

                final Item item = DataController.itemList.getItemByViewPosition(targetItemPosition);

                datePicker.updateDate(item.year, item.month - 1, item.day);
                spinner.setSelection(((GenreSpinnerAdapter) spinner.getAdapter()).getPositionByGenreId(item.genreId));

                editText_title.setText(item.title);
                editText_amount.setText(String.valueOf(item.amount));

                button_do.setText(getString(R.string.actionName_modify));
                button_do.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CoreItemData updatedItem = makeCoreItemData();
                        if (updatedItem == null) {
                            return;
                        }
                        DataController.itemList.updateItem(item.id, updatedItem.year, updatedItem.month, updatedItem.day, updatedItem.genreId, updatedItem.title, updatedItem.amount);
                        DataController.itemList.reloadList();
                        DataController.sumList.reloadList();
                        Toast.makeText(getApplicationContext(), R.string.resultMsg_modify, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;
        }

    }

    private CoreItemData makeCoreItemData() {

        String amount_str = editText_amount.getText().toString();
        if (amount_str.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.errorMsg_amountIsEmpty, Toast.LENGTH_SHORT).show();
            return null;
        }

        int amount;
        try {
            amount = Integer.parseInt(amount_str);
        } catch (NumberFormatException exc) {
            Toast.makeText(EditItemActivity.this, R.string.errorMsg_amountIsTooLarge, Toast.LENGTH_SHORT).show();
            return null;
        }

        return new CoreItemData(
                datePicker.getYear(),
                datePicker.getMonth() + 1,
                datePicker.getDayOfMonth(),
                DataController.genreList.getAllGenre().get(spinner.getSelectedItemPosition()).id,
                editText_title.getText().toString(),
                amount
        );
    }

    private class CoreItemData {
        int year, month, day, genreId, amount;
        String title;

        CoreItemData(int year, int month, int day, int genreId, String title, int amount) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.genreId = genreId;
            this.title = title;
            this.amount = amount;
        }
    }
}
