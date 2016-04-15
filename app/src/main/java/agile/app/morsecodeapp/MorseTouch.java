package agile.app.morsecodeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class MorseTouch extends AppCompatActivity {

    private boolean startRecording = false;
    private long unit = 200;
    long startTimeDown;
    long startTimeUp;
    long timeDown;
    long timeUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_touch);

        this.startTimeDown = 0;
        this.startTimeUp = 0;
        this.timeDown = 0;
        this.timeUp = 0;
    }

    public boolean onTouchEvent(MotionEvent event){
        if (!startRecording){
            this.startRecording = true;
        }
        if (startRecording){;
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.startTimeDown = 0;
                this.timeDown = 0;

                startTimeDown = System.currentTimeMillis();
                timeUp = System.currentTimeMillis() - startTimeUp;
            }

            if(event.getAction() == MotionEvent.ACTION_UP) {
                timeDown = System.currentTimeMillis() - startTimeDown;
                startTimeUp = System.currentTimeMillis();
            }
        }
        if (timeDown > 0 && timeUp > 0) {
            //End Word
            if (timeUp > 6 * unit && timeUp < 100 * unit) {
                Log.e("MorseTouch", "EndWord");
                Log.e("MorseTouch", "Space");
            }
            //Space
            if (timeUp > 2 * unit && timeUp < 6 * unit) {
                Log.e("MorseTouch", "Space");
            }
            //LongPress
            if (timeDown > 2 * unit) {
                Log.e("MorseTouch", "LongPress");
            }
            //ShortPress
            if (timeDown < 2 * unit) {
                Log.e("MorseTouch", "ShortPress");
            }
        }
        return false;
    }

}