package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.ViewDataController;

public class GenreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        ViewDataController.genreList.createListAdapter();
        ListView listView = (ListView) findViewById(R.id.listView_genre);
        listView.setAdapter(ViewDataController.genreList.getAdapter());
        ViewDataController.genreList.reloadList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] alertItems;
                if (position != 0)
                    alertItems = new String[]{getString(R.string.actionName_modify), getString(R.string.actionName_delete)};
                else
                    alertItems = new String[]{getString(R.string.actionName_modify)};
                new AlertDialog.Builder(GenreListActivity.this)
                        .setItems(alertItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final Genre target = ViewDataController.genreList.getGenreByViewPosition(position);

                                switch (which) {
                                    case 0:
                                        final ViewGroup v = getCustomAlertDialogView();
                                        final EditText e = getEditTextFromViewGroup(v);

                                        setValuesToViewsFromGenre(v, target);

                                        final AlertDialog dialog_modify = new AlertDialog.Builder(GenreListActivity.this)
                                                .setTitle(R.string.actionTitle_modifyGenre)
                                                .setView(v)
                                                .setNegativeButton(R.string.dialogNegative_cancel, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                .setPositiveButton(R.string.actionName_modify, null)
                                                .show();

                                        dialog_modify.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (e.getText().toString().equals("")) {
                                                    Toast.makeText(GenreListActivity.this, R.string.errorMsg_genreNameIsEmpty, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                ViewDataController.genreList.updateGenre(target.id, e.getText().toString(), e.getCurrentTextColor());
                                                ViewDataController.genreList.reloadList();
                                                ViewDataController.itemList.reloadList();
                                                Toast.makeText(GenreListActivity.this, R.string.resultMsg_modify, Toast.LENGTH_SHORT).show();
                                                dialog_modify.dismiss();
                                            }
                                        });
                                        break;

                                    case 1:
                                        new AlertDialog.Builder(GenreListActivity.this)
                                                .setMessage(R.string.dialogMsg_deleteGenre)
                                                .setNegativeButton(R.string.dialogNegative_cancel, null)
                                                .setPositiveButton(R.string.actionName_delete, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // 削除
                                                        ViewDataController.genreList.deleteGenreById(target.id);
                                                        ViewDataController.genreList.reloadList();
                                                        ViewDataController.itemList.reloadList();
                                                        Toast.makeText(GenreListActivity.this, R.string.resultMsg_delete, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ViewDataController.genreList.detachListAdapter();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("ジャンル追加");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {
            case 0:
                final ViewGroup v = getCustomAlertDialogView();
                final EditText e = getEditTextFromViewGroup(v);

                final AlertDialog dialog = new AlertDialog.Builder(GenreListActivity.this)
                        .setTitle(R.string.actionTitle_createGenre)
                        .setView(v)
                        .setNegativeButton(R.string.dialogNegative_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.actionName_create, null)
                        .show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (e.getText().toString().equals("")) {
                            Toast.makeText(GenreListActivity.this, R.string.errorMsg_genreNameIsEmpty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ViewDataController.genreList.addGenre(e.getText().toString(), e.getCurrentTextColor());
                        ViewDataController.genreList.reloadList();
                        Toast.makeText(GenreListActivity.this, R.string.resultMsg_create, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private ViewGroup getCustomAlertDialogView() {
        ViewGroup v = new LinearLayout(GenreListActivity.this);
        getLayoutInflater().inflate(R.layout.view_edit_genre, v);
        EditText editText = (EditText) v.findViewById(R.id.input_genreName);
        SeekBar s = (SeekBar) v.findViewById(R.id.input_color_r);
        s.setMax(255);
        s.setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.red));
        s = (SeekBar) v.findViewById(R.id.input_color_g);
        s.setMax(255);
        s.setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.green));
        s = (SeekBar) v.findViewById(R.id.input_color_b);
        s.setMax(255);
        s.setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.blue));
        return v;
    }

    private EditText getEditTextFromViewGroup(ViewGroup v) {
        return (EditText) v.findViewById(R.id.input_genreName);
    }

    private void setValuesToViewsFromGenre(final ViewGroup v, final Genre target) {
        final EditText e = getEditTextFromViewGroup(v);
        e.setText(target.name);
        e.setTextColor(Color.rgb(target.r, target.g, target.b));

        SeekBar s = (SeekBar) v.findViewById(R.id.input_color_r);
        s.setProgress(target.r);
        s = (SeekBar) v.findViewById(R.id.input_color_g);
        s.setProgress(target.g);
        s = (SeekBar) v.findViewById(R.id.input_color_b);
        s.setProgress(target.b);
    }

    private class ColorChangeListener implements SeekBar.OnSeekBarChangeListener {
        static final byte red = 1;
        static final byte green = 2;
        static final byte blue = 3;

        private EditText target;
        private byte colorId;

        ColorChangeListener(EditText editText, byte colorId) {
            target = editText;
            this.colorId = colorId;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int currentColor = target.getCurrentTextColor();
            int targetValue = 255 * seekBar.getProgress() / seekBar.getMax();
            int newColor = Color.BLACK;
            switch (colorId) {
                case red:
                    newColor = Color.rgb(targetValue, Color.green(currentColor), Color.blue(currentColor));
                    break;
                case green:
                    newColor = Color.rgb(Color.red(currentColor), targetValue, Color.blue(currentColor));
                    break;
                case blue:
                    newColor = Color.rgb(Color.red(currentColor), Color.green(currentColor), targetValue);
                    break;
            }
            target.setTextColor(newColor);
        }
    }
}
