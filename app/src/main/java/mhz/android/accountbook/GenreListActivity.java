package mhz.android.accountbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.zip.Inflater;

public class GenreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("ジャンル追加");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch( item.getOrder() ) {
            case 0:
                LinearLayout v = new LinearLayout(GenreListActivity.this);
                getLayoutInflater().inflate(R.layout.view_edit_genre, v);

                final EditText editText = (EditText)v.findViewById(R.id.input_genreName);
                ((SeekBar)v.findViewById(R.id.input_color_r)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.red));
                ((SeekBar)v.findViewById(R.id.input_color_g)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.green));
                ((SeekBar)v.findViewById(R.id.input_color_b)).setOnSeekBarChangeListener(new ColorChangeListener(editText, ColorChangeListener.blue));

                new AlertDialog.Builder(GenreListActivity.this)
                        .setTitle("ジャンル新規作成")
                        .setView(v)
                        .setNegativeButton("キャンセル", null)
                        .setPositiveButton("作成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 作成
                                Toast.makeText(GenreListActivity.this, "作成しました", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ColorChangeListener implements SeekBar.OnSeekBarChangeListener {
        static final byte red = 1;
        static final byte green = 2;
        static final byte blue = 3;

        private EditText target;
        private byte colorId;

        ColorChangeListener( EditText editText, byte colorId ) {
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
            switch( colorId ) {
                case red:
                    newColor = Color.rgb( targetValue, Color.green(currentColor), Color.blue(currentColor) );
                    break;
                case green:
                    newColor = Color.rgb( Color.red(currentColor), targetValue, Color.blue(currentColor) );
                    break;
                case blue:
                    newColor = Color.rgb( Color.red(currentColor), Color.green(currentColor), targetValue );
                    break;
            }
            target.setTextColor(newColor);
        }
    }
}
