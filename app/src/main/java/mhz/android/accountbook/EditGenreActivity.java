package mhz.android.accountbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import mhz.android.accountbook.data.DataController;
import mhz.android.accountbook.data.Genre;
import mhz.android.accountbook.data.Sum;

public class EditGenreActivity extends AppCompatActivity {

    private SeekBar s_r, s_g, s_b;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_genre);

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Button button_do = (Button) findViewById(R.id.button_do);

        input = (EditText) findViewById(R.id.input_genreName);
        s_r = (SeekBar) findViewById(R.id.input_color_r);
        s_r.setMax(255);
        s_r.setOnSeekBarChangeListener(new ColorChangeListener(input, ColorChangeListener.red));
        s_g = (SeekBar) findViewById(R.id.input_color_g);
        s_g.setMax(255);
        s_g.setOnSeekBarChangeListener(new ColorChangeListener(input, ColorChangeListener.green));
        s_b = (SeekBar) findViewById(R.id.input_color_b);
        s_b.setMax(255);
        s_b.setOnSeekBarChangeListener(new ColorChangeListener(input, ColorChangeListener.blue));

        final Intent intent = getIntent();
        final int request = intent.getIntExtra(C.IntentExtraName_RequestCode, -1);

        switch (request) {
            case C.RequestCode_AddGenre:
                setTitle(R.string.activity_title_addGenre);

                button_do.setText(R.string.actionName_add);
                button_do.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Genre g = pickInput();
                        DataController.sumList.addGenre(g.name, g.r, g.g, g.b);
                        DataController.sumList.reloadList();
                        DataController.itemList.reloadList();
                        Toast.makeText(EditGenreActivity.this, R.string.resultMsg_add, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

            case C.RequestCode_ModifyGenre:
                setTitle(R.string.activity_title_modifyGenre);

                final Sum target = DataController.sumList.getSumByViewPosition(intent.getIntExtra(C.IntentExtraName_TargetItemPosition, -1));
                applyToInput(target);

                button_do.setText(R.string.actionName_modify);
                button_do.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Genre g = pickInput();
                        DataController.sumList.updateGenre(target.genreId, g.name, g.r, g.g, g.b);
                        DataController.sumList.reloadList();
                        DataController.itemList.reloadList();
                        Toast.makeText(EditGenreActivity.this, R.string.resultMsg_modify, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;
        }
    }

    private void applyToInput(Sum s) {
        input.setText(s.genreName);
        input.setTextColor(s.color);
        s_r.setProgress(Color.red(s.color));
        s_g.setProgress(Color.green(s.color));
        s_b.setProgress(Color.blue(s.color));
    }

    private Genre pickInput() {
        return new Genre(0, input.getText().toString(), s_r.getProgress(), s_g.getProgress(), s_b.getProgress());
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
            int targetValue = seekBar.getProgress();
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
