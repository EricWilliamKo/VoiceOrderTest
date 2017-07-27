package com.example.eric.googlenowtest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eric.googlenowtest.model.Drink;
import com.example.eric.googlenowtest.model.VoiceOrderListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VoiceOrderListener{
    public static final int RESULT_GOOGLE_NOW = 999;
    private static final String TAG = "Google Now test";
    public TextView googlenowresult;
    public Spinner drinkName, ice_value, sugar_value;
    public ArrayAdapter<CharSequence> nameAdapter, iceAdapter, sugarAdapter;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        googlenowresult = (TextView) findViewById(R.id.googlenowresult);
        drinkName = (Spinner)findViewById(R.id.name_value);
        ice_value = (Spinner)findViewById(R.id.ice_value);
        sugar_value = (Spinner)findViewById(R.id.sugar_value);

        nameAdapter =
                ArrayAdapter.createFromResource(this, R.array.expression_name, R.layout.spinner_item);

        iceAdapter =
                ArrayAdapter.createFromResource(this, R.array.expression_ice, R.layout.spinner_item);

        sugarAdapter =
                ArrayAdapter.createFromResource(this, R.array.expression_sugar, R.layout.spinner_item);

        drinkName.setAdapter(nameAdapter);
        ice_value.setAdapter(iceAdapter);
        sugar_value.setAdapter(sugarAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlenowresult.setText("");
                startGoogleNow();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startGoogleNow() {
        Intent googleNowIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        googleNowIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.TAIWAN);
        googleNowIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.TAIWAN);

        try {
            Log.d("hey", "google now start");
            startActivityForResult(googleNowIntent, RESULT_GOOGLE_NOW);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this,
                    "Google now not found", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult():requestCode:" + requestCode + ",resultCode:" + resultCode);
        switch (requestCode) {
            case RESULT_GOOGLE_NOW:
                if (resultCode == RESULT_OK && null != data) {
                    Log.i(TAG, "RESULT_OK, data:" + data.toString());
                    ArrayList<String> matches = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    for (String match : matches) {
                        Log.i(TAG, "onActivityResult, text:" + match);
                        googlenowresult.append(match + "\n");
                        Drink drink = new Drink(this.getApplicationContext(), this);
                        drink.takeVoiceOrder(match);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setName(String name) {
        final int spinnerPosition = nameAdapter.getPosition(name);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drinkName.setSelection(spinnerPosition);
            }
        });
    }

    @Override
    public void setIce(String ice) {
        final int spinnerPosition = iceAdapter.getPosition(ice);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ice_value.setSelection(spinnerPosition);
            }
        });

    }

    @Override
    public void setSugar(String sugar) {
        final int spinnerPosition = sugarAdapter.getPosition(sugar);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sugar_value.setSelection(spinnerPosition);
            }
        });

    }
}
