package com.maangata.l.omdbapi;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by l on 8/2/17.
 */
public class Details extends AppCompatActivity {

    public TextView title, year, rated, released, runtime, genre, director, writer, actor, plot, language, country, awards, metascore, imdbrating, imdbvotes, imdbid;
    public ImageView poster;
    public ArrayList<String> myArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        setTheViews();
    }


    /*
    This method sets the different views that compound the Details_Activity.
     */
    public void setTheViews() {

        myArrayList = (ArrayList<String>) getIntent().getSerializableExtra("arrayWithMovieInfo");
        Movie myMovie = Methods.newMovie(myArrayList);

        title = setTheTextViews(title, R.id.titleDetails);
        year = setTheTextViews(year, R.id.yearDetails);
        rated = setTheTextViews(title, R.id.rateDetails);
        released = setTheTextViews(title, R.id.releasedDetails);
        runtime = setTheTextViews(title, R.id.runtimeDetails);
        genre = setTheTextViews(title, R.id.genreDetails);
        director = setTheTextViews(title, R.id.directorDetails);
        writer = setTheTextViews(title, R.id.writerDetails);
        actor = setTheTextViews(title, R.id.actorsDetails);
        plot = setTheTextViews(plot, R.id.plotDetails);
        language = setTheTextViews(title, R.id.languageDetails);
        country = setTheTextViews(title, R.id.countryDetails);
        awards = setTheTextViews(title, R.id.awardsDetails);
        metascore = setTheTextViews(title, R.id.metascoreDetails);
        imdbrating = setTheTextViews(title, R.id.imdbratingDetails);
        imdbvotes = setTheTextViews(imdbvotes, R.id.imdbvotesDetails);
        imdbid = setTheTextViews(imdbid, R.id.imdbidDetails);

        title.setText(myMovie.getTitle());
        year.setText("(" + myMovie.getYear() + ")");
        rated.setText(myMovie.getRated());
        rated.setTextColor(Color.RED);
        released.setText(myMovie.getReleased() + ".");
        runtime.setText(myMovie.getRuntime() + ".");
        genre.setText(myMovie.getGenre() + ".");
        director.setText(myMovie.getDirector() + ".");
        writer.setText("Writer: " + myMovie.getWriter() + ".");
        actor.setText("Actors: " + myMovie.getActor() + ".");

        // I want the plot to end with a dot (.). If it ends with it, do nothing, if it doesn't, add the dot at the end.
        if (myMovie.getPlot().substring(myMovie.getPlot().length() -1).equals(".")) {
            plot.setText(myMovie.getPlot());
        } else {

            plot.setText(myMovie.getPlot() + ".");
        }
        language.setText(myMovie.getLanguage() + ".");
        country.setText(myMovie.getCountry());
        awards.setText("Awards: " + myMovie.getAwards());
        metascore.setText(myMovie.getMetascore() + ".");
        imdbrating.setText(myMovie.getImdbrating() + ".");
        imdbvotes.setText(myMovie.getImdbvotes() + ".");
        imdbid.setText(myMovie.getImdbid() + ".");

        poster = (ImageView) findViewById(R.id.imageDetails);

        // I call the AsyncTask in order to get the image in runtime and not save it in the device. It uses another thread so it won't collapse the UI one.
        GetTheImages_Asynk myImageAsync = new GetTheImages_Asynk(poster, 1);
        myImageAsync.execute(myMovie.getPoster());
    }

    /**
     *
     Method created in order to symplify the declaration of the TextViews.
     * @param tv TextView that will create the method.
     * @param id It refers to the resource related to the TextView
     * @return It returns the TextView.
     */
    public TextView setTheTextViews(TextView tv, int id) {
        tv = (TextView) findViewById(id);
        return tv;
    }
}
