package com.maangata.l.omdbapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by l on 6/2/17.
 */
public class ResultsFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    public ResultsFragment() {
    }

    public ArrayList<String> mArrayWithJSONInfo;
    public final int FROM_FRAGMENT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewToReturn = inflater.inflate(R.layout.fragment_resultlist, container, false);

        // I get the information that I sent from the AsynkTask so that I get it to my fragment. It's an ArrayList with the parsed JSON info.
        mArrayWithJSONInfo = (ArrayList<String>) getActivity().getIntent().getSerializableExtra("mJSONArray");

        if (mArrayWithJSONInfo.isEmpty()) {
            // If nothing was retrieved from the OMDb, display a Toast and set it null
            Toast.makeText(getActivity().getApplicationContext(), "No information available. Maybe you misspelled the title?", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return null;
        } else {

            // If it had worked, create a Movie object and transform the info into a String that will be used by the Adapter to create the view.
            ListView listViewResults = (ListView) viewToReturn.findViewById(R.id.listViewResults);

            // Create the Adapter and set it to the ListView.
            ResultList_Adapter adapter = new ResultList_Adapter(getContext(), R.layout.item_adapter_results, mArrayWithJSONInfo);
            listViewResults.setAdapter(adapter);

            // When clicking in one the items of the ListView, start the Details activity, sending the ArrayList with the JSON info to the Deatils activity so it can be displayed.
            listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String[] mStringArrayToGetTheIMDbID = mArrayWithJSONInfo.get(i).split("sep");
                    GettingTheData_AsyncTask detailsOfMovie = new GettingTheData_AsyncTask(getContext(), null, null, FROM_FRAGMENT);
                    detailsOfMovie.execute(mStringArrayToGetTheIMDbID[4]);
                }
            });
            return viewToReturn;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}