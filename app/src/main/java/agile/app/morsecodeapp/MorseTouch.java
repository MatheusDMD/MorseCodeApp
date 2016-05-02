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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agile.app.morsecodeapp.morsetotext.Decoder;

import static agile.app.morsecodeapp.R.array;
import static agile.app.morsecodeapp.R.id;
import static agile.app.morsecodeapp.R.layout;
import static agile.app.morsecodeapp.R.string;


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
    private ImageView touchWarning;
    private TextView phoneView;
    private ImageButton sendButton;
    private ImageButton backspace;
    private ImageButton menuButton;
    private ImageButton contactButton;
    private CountDownTimer countdown;
    private View touchView;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> messageAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private String phone;
    private String contactPhone;
    private boolean fromContact;

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
        this.phoneView = (TextView) findViewById(id.textPhone);
        this.touchWarning = (ImageView) findViewById(id.touchWarning);
        this.sendButton = (ImageButton) findViewById(id.sendButton);
        this.backspace = (ImageButton) findViewById(id.backspace);
        this.menuButton = (ImageButton) findViewById(id.menuButton);
        this.contactButton = (ImageButton) findViewById(id.contactButton);
        this.contactList = (ListView) findViewById(id.contactList);
        this.messageList = (ListView) findViewById(id.messageList);
        this.touchView = (View) findViewById(id.touchView);
        this.fromContact = false;

        touchView.setOnTouchListener(this);


        mDrawerLayout = (DrawerLayout) findViewById(id.drawer_layout);
        mDrawerList = (ListView) findViewById(id.left_drawer);
        addDrawerItemsAll();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerLayout = (DrawerLayout)findViewById(id.drawer_layout);
        mActivityTitle = getTitle().toString();

        this.setupDrawer();

        String[] messagesArray = getResources().getStringArray(array.messages);
        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesArray);
        messageList.setAdapter(messageAdapter);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phrase = messageList.getItemAtPosition(position).toString();
                phraseView.setText(phrase);
            }
        });

        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor1);
        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1, android.R.id.text2};
        final SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor1, from, to);
        contactList.setAdapter(listAdapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listAdapter.getItem(position);
                phrase = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneView.setText(phrase);
                fromContact = true;
            }
        });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(send) {
                    phrase += mDrawerList.getItemAtPosition(position).toString().substring(0, 1).toLowerCase();
                    phoneView.setText(phrase);
                    Toast.makeText(MorseTouch.this,phoneView.getText().toString() , Toast.LENGTH_SHORT).show();
                }
                else{
                    phrase += mDrawerList.getItemAtPosition(position).toString().substring(0, 1).toLowerCase();
                    phraseView.setText(phrase);
                    Toast.makeText(MorseTouch.this,phraseView.getText().toString() , Toast.LENGTH_SHORT).show();
                }

            }
        });


        menuButton.setOnClickListener(new View.OnClickListener(){
            boolean showing = false;
            @Override
            public void onClick(View view) {
                if (!showing){
                    touchWarning.setVisibility(View.GONE);
                    contactList.setVisibility(View.GONE);
                    messageList.setVisibility(View.VISIBLE);
                    showing = true;
                } else {
                    touchWarning.setVisibility(View.VISIBLE);
                    contactList.setVisibility(View.GONE);
                    messageList.setVisibility(View.GONE);
                    showing = false;
                }
            }
            });

        contactButton.setOnClickListener(new View.OnClickListener(){
            boolean showing = false;

            @Override
            public void onClick(View view) {
                if (!showing){
                    touchWarning.setVisibility(View.GONE);
                    contactList.setVisibility(View.VISIBLE);
                    messageList.setVisibility(View.GONE);
                    showing = true;
                } else {
                    touchWarning.setVisibility(View.VISIBLE);
                    contactList.setVisibility(View.GONE);
                    messageList.setVisibility(View.GONE);
                    showing = false;
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  countdown.cancel();
                  contactList.setVisibility(View.GONE);
                  messageList.setVisibility(View.GONE);
                  touchWarning.setVisibility(View.VISIBLE);
                  if (send) {
                      addDrawerItemsAll();
                      menuButton.setVisibility(View.VISIBLE);
                      contactButton.setVisibility(View.GONE);
                      SmsManager manager = SmsManager.getDefault();
                      if(!fromContact) {
                          phone = phoneView.getText().toString();
                      }
                      else{
                          phone = contactPhone;
                      }
                      if (PhoneNumberUtils.isWellFormedSmsAddress(phone)) {
                          if (phraseView.getText().toString() == "") {
                              Toast.makeText(MorseTouch.this, "Empty Message", Toast.LENGTH_SHORT).show();
                          } else {
                              manager.sendTextMessage(phone, null, phraseView.getText().toString(), null, null);
                              Toast.makeText(MorseTouch.this, "Sent", Toast.LENGTH_SHORT).show();
                          }
                      } else {
                          Toast.makeText(MorseTouch.this, "Invalid telephone number", Toast.LENGTH_SHORT).show();
                      }
                      fromContact = false;
                      phoneView.setVisibility(View.GONE);
                      phraseView.setText("");
                      phrase = "";
                      send = false;
                  } else {
                      addDrawerItemsNum();
                      menuButton.setVisibility(View.GONE);
                      contactButton.setVisibility(View.VISIBLE);
                      phrase = "";
                      morseText.clear();
                      phoneView.setText("Telephone number");
                      phoneView.setVisibility(View.VISIBLE);
                      Toast.makeText(MorseTouch.this, "Type the telephone number", Toast.LENGTH_SHORT).show();
                      send = true;
                  }
              }
          });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                countdown.cancel();
                if (send) {
                    if (!fromContact) {
                        if (phrase.length() != 0) {
                            phrase = phrase.substring(0, phrase.length() - 1);
                            phoneView.setText(phrase);
                            morseText.clear();
                            if (phoneView.getText().toString().length() == 0) {
                                phoneView.setText("Telephone number");
                            }
                        } else {
                            phoneView.setVisibility(View.GONE);
                            phrase = phraseView.getText().toString();
                            menuButton.setVisibility(View.VISIBLE);
                            contactButton.setVisibility(View.GONE);
                            contactList.setVisibility(View.GONE);
                            messageList.setVisibility(View.GONE);
                            touchWarning.setVisibility(View.VISIBLE);
                            addDrawerItemsAll();
                            send = false;
                        }
                    } else {
                        phoneView.setVisibility(View.GONE);
                        phrase = phraseView.getText().toString();
                        menuButton.setVisibility(View.VISIBLE);
                        contactButton.setVisibility(View.GONE);
                        contactList.setVisibility(View.GONE);
                        messageList.setVisibility(View.GONE);
                        touchWarning.setVisibility(View.VISIBLE);
                        send = false;
                        fromContact = false;
                        addDrawerItemsAll();
                    }
                } else {
                    if (phrase.length() != 0) {
                        phrase = phrase.substring(0, phrase.length() - 1);
                        phraseView.setText(phrase);
                        morseText.clear();
                        if (phraseView.getText().toString().length() == 0) {
                            phraseView.setText("Text Preview");
                        }
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
                        phoneView.setText(phrase);
                        morseText.clear();
                    }
                }
            }
        };
    }
    private void addDrawerItemsAll() {
        String[] morseArray = getResources().getStringArray(array.morse);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, morseArray);
        mDrawerList.setAdapter(mAdapter);

    }

    private void addDrawerItemsNum() {
        String[] morseArray = getResources().getStringArray(array.morseNumbers);
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
            if(fromContact){
                Toast.makeText(MorseTouch.this, "You have already chosen a contact", Toast.LENGTH_SHORT).show();
            }
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
                    phoneView.setText(phrase);
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
                        phoneView.setText(phrase);
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
