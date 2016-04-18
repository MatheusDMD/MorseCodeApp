package agile.app.morsecodeapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;


public class MorseTouch extends AppCompatActivity {

    private boolean startRecording = false;
    private long unit = 300;
    long startTimeDown;
    long startTimeUp;
    long timeDown;
    long timeUp;
    List<String> morseText;
    Decoder decoder;
    String phrase;
    TextView phraseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_touch);

        this.startTimeDown = 0;
        this.startTimeUp = 0;
        this.timeDown = 0;
        this.timeUp = 0;
        this.morseText = new ArrayList<String>();
        this.decoder = new Decoder();
        this.phrase = "";
        this.phraseView = (TextView)findViewById(R.id.textMorse);
    }

    public boolean onTouchEvent(MotionEvent event){
        if (!startRecording){
            this.startRecording = true;
        }
        if (startRecording){;
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                setActivityBackgroundColor(Color.parseColor("#2C2C2C"));
                this.startTimeDown = 0;
                this.timeDown = 0;

                startTimeDown = System.currentTimeMillis();
                timeUp = System.currentTimeMillis() - startTimeUp;
            }

            if(event.getAction() == MotionEvent.ACTION_UP) {
                setActivityBackgroundColor(Color.parseColor("#FFFFFF"));
                timeDown = System.currentTimeMillis() - startTimeDown;
                startTimeUp = System.currentTimeMillis();
            }
        }
        if (timeDown > 0 && timeUp > 0) {
            //End Word
            if (timeUp > 6 * unit && timeUp < 100 * unit) {
                Log.e("MorseTouch", "EndWord");
                Log.e("MorseTouch", "Space");
                morseText.add("/");
                Log.e("MorseTouch", morseText.toString());
                phrase += decoder.decodeMorse(morseText);
                phraseView.setText(phrase);
                Log.e("MorseTouch", phrase);
                morseText.clear();
            }
            //Space
            if (timeUp > 2 * unit && timeUp < 6 * unit) {
                Log.e("MorseTouch", "Space");
                morseText.add("/");
            }
            //LongPress
            if (timeDown > 2 * unit) {
                Log.e("MorseTouch", "LongPress");
                morseText.add("-");
            }
            //ShortPress
            if (timeDown < 2 * unit) {
                Log.e("MorseTouch", "ShortPress");
                morseText.add(".");
            }
        }
        return false;
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}
