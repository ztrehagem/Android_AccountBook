package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
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

import java.util.zip.Inflater;

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
                new AlertDialog.Builder(GenreListActivity.this)
                        .setItems(new String[]{"修正", "削除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final Genre target = ViewDataController.genreList.getGenreByViewPosition(position);

                                switch (which) {
                                    case 0:
                                        final Pair<ViewGroup, EditText> vs = getCustomAlertDialogView();

                                        vs.second.setText(target.name);
                                        vs.second.setTextColor(Color.rgb(target.r, target.g, target.b));

                                        SeekBar s = (SeekBar) vs.first.findViewById(R.id.input_color_r);
                                        s.setProgress(target.r * s.getMax() / 256);
                                        s = (SeekBar) vs.first.findViewById(R.id.input_color_g);
                                        s.setProgress(target.g * s.getMax() / 256);
                                        s = (SeekBar) vs.first.findViewById(R.id.input_color_b);
                                        s.setProgress(target.b * s.getMax() / 256);

                                        final AlertDialog dialog_modify = new AlertDialog.Builder(GenreListActivity.this)
                                                .setTitle("ジャンル修正")
                                                .setView(vs.first)
                                                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                .setPositiveButton("修正", null)
                                                .show();

                                        dialog_modify.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (vs.second.getText().toString().equals("")) {
                                                    Toast.makeText(GenreListActivity.this, "ジャンル名を入力してください", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                ViewDataController.genreList.updateGenre(target.id, vs.second.getText().toString(), vs.second.getCurrentTextColor());
                                                ViewDataController.genreList.reloadList();
                                                Toast.makeText(GenreListActivity.this, "修正しました", Toast.LENGTH_SHORT).show();
                                                dialog_modify.dismiss();
                                            }
                                        });
                                        break;

                                    case 1:
                                        new AlertDialog.Builder(GenreListActivity.this)
                                                .setMessage("ジャンルを削除しますか？")
                                                .setNegativeButton("キャンセル", null)
                                                .setPositiveButton("削除", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // 削除
                                                        ViewDataController.genreList.deleteGenreById(target.id);
                                                        ViewDataController.genreList.reloadList();
                                                        Toast.makeText(GenreListActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
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
                final Pair<ViewGroup, EditText> vs = getCustomAlertDialogView();

                final AlertDialog dialog = new AlertDialog.Builder(GenreListActivity.this)
                        .setTitle("ジャンル新規作成")
                        .setView(vs.first)
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("作成", null)
                        .show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vs.second.getText().toString().equals("")) {
                            Toast.makeText(GenreListActivity.this, "ジャンル名を入力してください", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ViewDataController.genreList.addGenre(vs.second.getText().toString(), vs.second.getCurrentTextColor());
                        ViewDataController.genreList.reloadList();
                        Toast.makeText(GenreListActivity.this, "作成しました", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Pair<ViewGroup, EditText> getCustomAlertDialogView() {
        ViewGroup v = new LinearLayout(GenreListActivity.this);
        getLayoutInflater().inflate(R.layout.view_edit_genre, v);
        EditText editText = (EditText) v.findViewById(R.id.input_genreName);
        ((SeekBar) v.findViewById(R.id.input_color_r)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.red));
        ((SeekBar) v.findViewById(R.id.input_color_g)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.green));
        ((SeekBar) v.findViewById(R.id.input_color_b)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.blue));
        return new Pair<>(v, editText);
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
