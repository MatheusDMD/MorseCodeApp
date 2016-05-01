package agile.app.morsecodeapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;

import static agile.app.morsecodeapp.R.*;



public class MorseTouch extends AppCompatActivity implements View.OnTouchListener {
    private boolean send;
    private boolean startRecording = false;
    private long unit = 300;
    long startTimeDown;
    long startTimeUp;
    long timeDown;
    long timeUp;
    private List<String> morseText;
    private ListView contactList;
    private ListView messageList;
    private Decoder decoder;
    private String phrase;
    private TextView phraseView;
    private TextView phraseViewTouch;
    private TextView touchWarning;
    private TextView phoneText;
    private ImageButton sendButton;
    private ImageButton backspace;
    private ImageButton menuButton;
    private CountDownTimer countdown;
    private View touchView;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    Cursor cursor1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_morse_touch);

        int smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(smsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }

        int contactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(contactPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        }

        this.send = false;
        this.startTimeDown = 0;
        this.startTimeUp = 0;
        this.timeDown = 0;
        this.timeUp = 0;
        this.morseText = new ArrayList<String>();
        this.decoder = new Decoder();
        this.phrase = "";
        this.phraseView = (TextView) findViewById(id.textMorse);
        this.phraseViewTouch = (TextView) findViewById(id.textMorse);
        this.phoneText = (TextView) findViewById(id.textPhone);
        this.touchWarning = (TextView) findViewById(id.touchWarning);
        this.sendButton = (ImageButton) findViewById(id.sendButton);
        this.backspace = (ImageButton) findViewById(id.backspace);
        this.menuButton = (ImageButton) findViewById(id.menuButton);
        this.contactList = (ListView) findViewById(id.contactList);
        this.messageList = (ListView) findViewById(id.messageList);
        this.touchView = (View) findViewById(id.touchView);

        touchView.setOnTouchListener(this);


        mDrawerLayout = (DrawerLayout) findViewById(id.drawer_layout);
        mDrawerList = (ListView) findViewById(id.left_drawer);
        addDrawerItems();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerLayout = (DrawerLayout)findViewById(id.drawer_layout);
        mActivityTitle = getTitle().toString();

        this.setupDrawer();



        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor1);
        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor1, from, to);
        contactList.setAdapter(listAdapter);


        menuButton.setOnClickListener(new View.OnClickListener(){
            boolean showing = false;

            @Override
            public void onClick(View view) {
                if(!showing) {
                    touchWarning.setVisibility(View.GONE);
                    contactList.setVisibility(View.VISIBLE);
                    showing = true;
                }
                else{
                    touchWarning.setVisibility(View.VISIBLE);
                    contactList.setVisibility(View.GONE);
                    showing = false;
                }
            }
            });

        sendButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  countdown.cancel();
                  if (send) {
                      SmsManager manager = SmsManager.getDefault();
                      String phone = phoneText.getText().toString();
                      if (PhoneNumberUtils.isWellFormedSmsAddress(phone)) {
                          if (phrase == "") {
                              Toast.makeText(MorseTouch.this, "Empty Message", Toast.LENGTH_SHORT).show();
                          } else {
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
                  } else {
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
            public void onClick (View view){
            countdown.cancel();
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
        countdown = new CountDownTimer(2*unit, 100) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                if (!send) {
                    morseText.add(" ");
                    phrase += decoder.decodeMorse(morseText);
                    phraseView.setText(phrase);
                    morseText.clear();
                }
                else{
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
            }
        };
    }
    private void addDrawerItems() {
        String[] morseArray = getResources().getStringArray(array.morse);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, morseArray);
        mDrawerList.setAdapter(mAdapter);

    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                string.drawer_open, string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Morse-Dictionary");
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)){
            //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onTouch(View touchView, MotionEvent event) {
            if (!startRecording) {
                this.startRecording = true;
                phraseViewTouch.setText("");
            }
            if (startRecording) {
                Log.e("MorseTouch", String.valueOf(event.getActionMasked()));
                int action = event.getAction() & MotionEvent.ACTION_MASK;
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                    setActivityBackgroundColor(Color.parseColor("#2C2C2C"));
                    this.startTimeDown = 0;
                    this.timeDown = 0;
                    startTimeDown = System.currentTimeMillis();
                    timeUp = System.currentTimeMillis() - startTimeUp;
                    countdown.cancel();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                    setActivityBackgroundColor(Color.parseColor("#E0E0E0"));
                    timeDown = System.currentTimeMillis() - startTimeDown;
                    startTimeUp = System.currentTimeMillis();
                    countdown.start();
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
        View view = findViewById(id.thirdLayout);
        view.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
