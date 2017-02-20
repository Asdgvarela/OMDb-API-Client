package com.maangata.l.omdbapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by l on 6/2/17.
 */
public class ResultList_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);

        // Just setting the fragment into the activity that contains it.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new ResultsFragment())
                    .commit();
        }
    }
}
