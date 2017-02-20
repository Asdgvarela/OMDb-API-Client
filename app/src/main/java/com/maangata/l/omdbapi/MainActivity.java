package com.maangata.l.omdbapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar bar;
    public TextView text, textWhileSearching;
    public EditText edit;
    public Button goButton;
    public final int FROM_MAINACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheActivity();
    }

    /*
    This method sets all the visual items in the Main Activity, as well as the
     */
    public void setTheActivity() {

        text = (TextView) findViewById(R.id.titleText);

        edit = (EditText) findViewById(R.id.titleEdit);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        textWhileSearching = (TextView) findViewById(R.id.textInMainWhileSearching);

        goButton = (Button) findViewById(R.id.buttonGo);
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (edit.getText().toString().isEmpty()) {
                    /*
                    If there's no text in the EditText where the title is written, it gives back a Toast inviting the user to write it.
                    Otherwise it launches teh AsyncTask that will retrieve the JSON info about the movie.
                    */
                    Toast.makeText(MainActivity.this, R.string.noMovie, Toast.LENGTH_SHORT).show();
                } else {
                    // Launching the AsynTask that will retrieve the JSON info from the OMDb: an array with the hits.
                    GettingTheData_AsyncTask mJSONInfo = new GettingTheData_AsyncTask(getApplicationContext(), bar, textWhileSearching, FROM_MAINACTIVITY);
                    mJSONInfo.execute(edit.getText().toString().trim());
                    // While it's searching it displays a message with the name of the movie.
                    textWhileSearching.setText("Searching for the movie... " + edit.getText().toString().trim());
                    // It clears the EditText field.
                    edit.setText("");

                    // It hides the soft keyboard.
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }
}
