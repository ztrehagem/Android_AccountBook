package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.Item;
import mhz.android.accountbook.data.ViewDataController;
import mhz.android.accountbook.db.MySQLiteController;

public class EditItemActivity extends AppCompatActivity {

    private MySQLiteController db;

    private ArrayList<Genre> allGenreList;
    private Spinner spinner;
    private DatePicker datePicker;
    private EditText editText_title;
    private EditText editText_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        final Intent intent = getIntent();
        final int requestCode = intent.getIntExtra("request", 0);


        //** initialize
        allGenreList = MySQLiteController.getInstance().getAllGenre();
        spinner = (Spinner) findViewById(R.id.spinner);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        editText_title = (EditText) findViewById(R.id.input_title);
        editText_amount = (EditText) findViewById(R.id.input_amount);


        //** view
        spinner.setAdapter(new EditItemSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGenreList));


        //** event listener
        switch (requestCode) {
            case R.integer.requestCode_AddItem:
                setTitle(R.string.activity_title_addItem);
                findViewById(R.id.buttons_modify).setVisibility(View.GONE);

                findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CoreItemData item = makeCoreItemData();
                        if (item == null) {
                            return;
                        }

                        ViewDataController.itemList.addItem(item.year, item.month, item.day, item.genreId, item.title, item.amount);
                        ViewDataController.itemList.reloadList();
                        Toast.makeText(getApplicationContext(), R.string.resultMsg_add, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

            case R.integer.requestCode_ModifyItem:
                setTitle(R.string.activity_title_modifyItem);
                findViewById(R.id.buttons_add).setVisibility(View.GONE);

                final int targetItemPosition = intent.getIntExtra("target_item_position", -1);
                if (targetItemPosition == -1)
                    throw new RuntimeException();

                final Item item = ViewDataController.itemList.getItemByViewPosition(targetItemPosition);

                datePicker.updateDate(item.year, item.month - 1, item.day);
                spinner.setSelection(((EditItemSpinnerAdapter) spinner.getAdapter()).getPositionByGenreId(item.genreId));

                editText_title.setText(item.title);
                editText_amount.setText(String.valueOf(item.amount));

                findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(EditItemActivity.this)
                                .setMessage(R.string.dialogMsg_deleteItem)
                                .setPositiveButton(R.string.actionName_delete, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewDataController.itemList.deleteItemById(item.id);
                                        ViewDataController.itemList.reloadList();
                                        Toast.makeText(getApplicationContext(), R.string.resultMsg_delete, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .setNegativeButton(R.string.dialogNegative_cancel, null)
                                .show();
                    }
                });

                findViewById(R.id.button_modify).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CoreItemData updatedItem = makeCoreItemData();
                        if (updatedItem == null) {
                            Toast.makeText(getApplicationContext(), R.string.errorMsg_amountIsEmpty, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ViewDataController.itemList.updateItem(item.id, updatedItem.year, updatedItem.month, updatedItem.day, updatedItem.genreId, updatedItem.title, updatedItem.amount);
                        ViewDataController.itemList.reloadList();
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
                allGenreList.get(spinner.getSelectedItemPosition()).id,
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
