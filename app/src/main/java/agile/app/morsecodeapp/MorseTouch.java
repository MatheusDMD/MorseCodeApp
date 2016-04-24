package agile.app.morsecodeapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;


public class MorseTouch extends AppCompatActivity {
    private boolean send;
    private boolean startRecording = false;
    private long unit = 300;
    long startTimeDown;
    long startTimeUp;
    long timeDown;
    long timeUp;
    private List<String> morseText;
    private Decoder decoder;
    private String phrase;
    private TextView phraseView;
    private TextView phraseViewTouch;
    private TextView phoneText;
    private Button sendButton;
    private Button backspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_touch);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
        this.send = false;
        this.startTimeDown = 0;
        this.startTimeUp = 0;
        this.timeDown = 0;
        this.timeUp = 0;
        this.morseText = new ArrayList<String>();
        this.decoder = new Decoder();
        this.phrase = "";
        this.phraseView = (TextView) findViewById(R.id.textMorse);
        this.phraseViewTouch = (TextView) findViewById(R.id.textMorse);
        this.phoneText = (TextView) findViewById(R.id.textPhone);
        this.sendButton = (Button) findViewById(R.id.sendButton);
        this.backspace = (Button) findViewById(R.id.backspace);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send) {
                    SmsManager manager = SmsManager.getDefault();
                    String phone = phoneText.getText().toString();
                    if (PhoneNumberUtils.isWellFormedSmsAddress(phone)) {
                        if(phrase == ""){
                            Toast.makeText(MorseTouch.this, "Empty Message", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            manager.sendTextMessage(phone, null, phraseView.getText().toString(), null, null);
                            Toast.makeText(MorseTouch.this, "Sent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MorseTouch.this, "Invalid telephone number", Toast.LENGTH_SHORT).show();
                    }
                    phoneText.setVisibility(View.GONE);
                    phraseView.setText("");
                    phrase = "";
                    send = false;
                }
                else{
                    phrase = "";
                    morseText.clear();
                    phoneText.setText("Telephone number");
                    phoneText.setVisibility(View.VISIBLE);
                    Toast.makeText(MorseTouch.this, "Type the telephone number", Toast.LENGTH_SHORT).show();
                    send = true;
                }
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send) {
                    if (phrase.length() != 0) {
                        phrase = phrase.substring(0, phrase.length() - 1);
                        phoneText.setText(phrase);
                        morseText.clear();
                    } else {
                        phoneText.setVisibility(View.GONE);
                        phraseView.setText("");
                        phrase = "";
                        send = false;
                    }
                } else {
                    if (phrase.length() != 0) {
                        phrase = phrase.substring(0, phrase.length() - 1);
                        phraseView.setText(phrase);
                        morseText.clear();
                    }
                }
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
            if (!startRecording) {
                this.startRecording = true;
                phraseViewTouch.setText("");
            }
            if (startRecording) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setActivityBackgroundColor(Color.parseColor("#2C2C2C"));
                    this.startTimeDown = 0;
                    this.timeDown = 0;

                    startTimeDown = System.currentTimeMillis();
                    timeUp = System.currentTimeMillis() - startTimeUp;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setActivityBackgroundColor(Color.parseColor("#E0E0E0"));
                    timeDown = System.currentTimeMillis() - startTimeDown;
                    startTimeUp = System.currentTimeMillis();
                }
            }
        if (!send) {
            if (timeDown > 0 && timeUp > 0) {
                if (timeDown > 5 * unit) {
                    Log.e("MorseTouch", "EndPhrase");
                    morseText.add(" ");
                    phrase += decoder.decodeMorse(morseText);
                    phrase += ".";
                    phraseView.setText(phrase);
                    Log.e("MorseTouch", phrase);
                    morseText.clear();
                } else {
                    //End Word
                    if (timeUp > 6 * unit && timeUp < 100 * unit) {
                        Log.e("MorseTouch", "EndWord");
                        Log.e("MorseTouch", "Space");
                        morseText.add(" ");
                        Log.e("MorseTouch", morseText.toString());
                        phrase += decoder.decodeMorse(morseText);
                        phrase += " ";
                        phraseView.setText(phrase);
                        Log.e("MorseTouch", phrase);
                        morseText.clear();
                    }
                    //Space
                    if (timeUp > 2 * unit && timeUp < 6 * unit) {
                        Log.e("MorseTouch", "Space");
                        morseText.add(" ");
                        phrase += decoder.decodeMorse(morseText);
                        phraseView.setText(phrase);
                        morseText.clear();
                    }
                    //LongPress
                    if (timeDown > unit && timeDown < 5 * unit) {
                        Log.e("MorseTouch", "LongPress");
                        morseText.add("-");
                    }
                    //ShortPress
                    if (timeDown < unit) {
                        Log.e("MorseTouch", "ShortPress");
                        morseText.add(".");
                    }
                }
            }
        } else {
            if (timeDown > 0 && timeUp > 0) {
                if (timeDown > 5 * unit) {
                    Log.e("MorseTouch", "EndPhone");
                    morseText.add(" ");
                    phrase += decoder.decodeMorse(morseText);
                    phoneText.setText(phrase);
                    Toast.makeText(MorseTouch.this, phrase, Toast.LENGTH_SHORT).show();
                    Log.e("MorseTouch", phrase);
                    morseText.clear();
                }
                //Space
                if (timeUp > 2 * unit) {
                    Log.e("MorseTouch", "NumSpace");
                    if (morseText.size() != 5) {
                        //Toast.makeText(MorseTouch.this, "Invalid Digit", Toast.LENGTH_SHORT).show();
                        morseText.clear();
                    } else {
                        morseText.add(" ");
                        phrase += decoder.decodeMorse(morseText);
                        phoneText.setText(phrase);
                        morseText.clear();
                    }
                }
                //LongPress
                if (timeDown > unit && timeDown < 5 * unit) {
                    Log.e("MorseTouch", "NumLongPress");
                    morseText.add("-");
                }
                //ShortPress
                if (timeDown < unit) {
                    Log.e("MorseTouch", "NumShortPress");
                    morseText.add(".");
                }
            }
        }
        return false;
    }

    public void setActivityBackgroundColor(int color) {
        View view = findViewById(R.id.thirdLayout);
        view.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
