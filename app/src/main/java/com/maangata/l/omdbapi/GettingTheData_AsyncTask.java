package com.maangata.l.omdbapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by l on 7/2/17.
 */

public class GettingTheData_AsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

    private Context mContext;
    private ProgressBar bar;
    private TextView txtView;

    /**
     * It creates a constructor with the arguments that are taken from the Activity or Fragment where it was launched.
     * @param context It gives me the context of the Activity or Fragment where the AsyncTask was launched.
     * @param bar It gives me the ProgressBar that will be shown in the MainActivity while it searches.
     * @param txtView It gives me the TextView that will be shown while it's searching in the MainActivity.
     */
    public GettingTheData_AsyncTask(Context context, ProgressBar bar, TextView txtView) {
        this.mContext = context;
        this.bar = bar;
        this.txtView = txtView;
    }

    /**
     * This method parses the info from the JSON string. It's been observed that this API only gives one movie as a result, and not a list of movies as it was desirable.
     * Having that into account, I have parsed the JSON file so that the info that it provides is stored in an ArrayList<String>
     * @param stringJSON That's the JSON snippet that's been retrieved in doInBackground() method. It's raw, coming directly from it with no modifications.
     * @return It returns an ArrayList<String> that contains the parsed info from the JSON file. That ArrayList will be suitable to create a Movie object.
     */
    public ArrayList<String> extractInfoFromJSON(String stringJSON) {

        final String ACCESS_TITLE ="Title";
        final String ACCESS_YEAR = "Year";
        final String ACCESS_RATED = "Rated";
        final String ACCESS_RELEASED = "Released";
        final String ACCESS_RUNTIME = "Runtime";
        final String ACCESS_GENRE = "Genre";
        final String ACCESS_DIRECTOR = "Director";
        final String ACCESS_WRITER = "Writer";
        final String ACCESS_ACTOR = "Actors";
        final String ACCESS_PLOT = "Plot";
        final String ACCESS_LANGUAGE = "Language";
        final String ACCESS_COUNTRY = "Country";
        final String ACCESS_AWARDS = "Awards";
        final String ACCESS_POSTER = "Poster";
        final String ACCESS_METASCORE = "Metascore";
        final String ACCESS_IMDBRATING = "imdbRating";
        final String ACCESS_IMDBVOTES = "imdbVotes";
        final String ACCESS_IMDBID = "imdbID";
        final String ACCESS_TYPE = "Type";
        final String ACCESS_RESPONSE = "Response";

        ArrayList<String> mListToPass = new ArrayList<>();


        try {
            JSONObject mJSONString = new JSONObject(stringJSON);

            mListToPass = new ArrayList<>();
            mListToPass.add(0, mJSONString.getString(ACCESS_TITLE));
            mListToPass.add(1, mJSONString.getString(ACCESS_YEAR));
            mListToPass.add(2, mJSONString.getString(ACCESS_RATED));
            mListToPass.add(3, mJSONString.getString(ACCESS_RELEASED));
            mListToPass.add(4, mJSONString.getString(ACCESS_RUNTIME));
            mListToPass.add(5, mJSONString.getString(ACCESS_GENRE));
            mListToPass.add(6, mJSONString.getString(ACCESS_DIRECTOR));
            mListToPass.add(7, mJSONString.getString(ACCESS_WRITER));
            mListToPass.add(8, mJSONString.getString(ACCESS_ACTOR));
            mListToPass.add(9, mJSONString.getString(ACCESS_PLOT));
            mListToPass.add(10, mJSONString.getString(ACCESS_LANGUAGE));
            mListToPass.add(11, mJSONString.getString(ACCESS_COUNTRY));
            mListToPass.add(12, mJSONString.getString(ACCESS_AWARDS));
            mListToPass.add(13, mJSONString.getString(ACCESS_POSTER));
            mListToPass.add(14, mJSONString.getString(ACCESS_METASCORE));
            mListToPass.add(15, mJSONString.getString(ACCESS_IMDBRATING));
            mListToPass.add(16, mJSONString.getString(ACCESS_IMDBVOTES));
            mListToPass.add(17, mJSONString.getString(ACCESS_IMDBID));
            mListToPass.add(18, mJSONString.getString(ACCESS_TYPE));
            mListToPass.add(19, mJSONString.getString(ACCESS_RESPONSE));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mListToPass;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // It sets visible the Progress Bar in MainActivity while it searches the title.
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        final String OMDBURL = "http://www.omdbapi.com/?";
        final String QTITLE = "t";
        final String QTYPE = "type";
        final String QFORMAT = "r";
        String mJSONString = null;

        HttpURLConnection mURLConnection = null;
        BufferedReader mReader = null;

        // Setting the Url where it will retrieve the information, as well as the different parameters.
        // I set that I want to find movies, instead of episodes or series, and I want the file in JSON format, instead of XML.
        try {
            Uri buildingURI = Uri.parse(OMDBURL).buildUpon()
                    .appendQueryParameter(QTITLE, strings[0])
                    .appendQueryParameter(QTYPE, "movie")
                    .appendQueryParameter(QFORMAT, "json")
                    .build();

            URL mURL = new URL(buildingURI.toString());

            // Setting up the connection to the server.
            mURLConnection = (HttpURLConnection) mURL.openConnection();
            mURLConnection.setRequestMethod("GET");
            mURLConnection.connect();

            // Retrieveing the info that was asked for.
            InputStream mInputFromOMDb = mURLConnection.getInputStream();
            StringBuffer mBuffer = new StringBuffer();

            if (mInputFromOMDb == null) {
                // If nothing has been retrieved it's set null, since it makes no sense to finish the process.
                return null;
            }

            mReader = new BufferedReader(new InputStreamReader(mInputFromOMDb));

            String line;
            if ((line = mReader.readLine()) != null) {
                // It's unnecesary to add the \n since it's JSON, but it will make it easier to read.
                mBuffer.append(line + "\n");
            }

            if (mBuffer.length() == 0) {
                // Again, if nothing has been retrieved it's set null, since it makes no sense to finish the process.
                return null;
            }

            mJSONString = mBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            // If there's been an error and nothing has been retrieved it's set null, since it makes no sense to finish the process.
            return null;

        } finally {
            // After retrieving the JSON file we close the connection with the server.
            if (mURLConnection != null) {
                mURLConnection.disconnect();
            }
            if (mReader != null) {
                try {
                    // The reader also has to be closed when we're done.
                    mReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // The JSON info is parsed, the ArrayList created, and it's what I return.
        ArrayList<String> mResultsArray = extractInfoFromJSON(mJSONString);
        return mResultsArray;
    }

    @Override
    protected void onPostExecute(ArrayList<String> mArray) {

        // This method will determine what it is done in the activity where the AsynTask was launched.
        // If I get a null ListArray, with no info, I want to throw a Toast so that the user will know - and the app won't crash.
        if (mArray == null) {
            Toast.makeText(mContext, "No data found! Search again", Toast.LENGTH_SHORT).show();
        } else {
            // If the ArrayList contains the info that was being searched, the ResultList_activity is launched, taking the ArrayList as an extra.
            Intent i = new Intent(mContext, ResultList_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("mJSONArray", mArray);
            mContext.startActivity(i);

        }
        // Also, in MainActivity, I want to set invisible the ProgressBar and the TextView that was showing the search message.
        bar.setVisibility(View.GONE);
        txtView.setText("");
    }
}