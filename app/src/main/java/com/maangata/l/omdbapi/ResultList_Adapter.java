package com.maangata.l.omdbapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by l on 7/2/17.
 */

/**
 * It's a custom adapter created to show the info about a movie in a ListView. I customize it in order to show it in the way I like.
 */
public class ResultList_Adapter extends ArrayAdapter<String> {

    public ArrayList<String> myList;
    public Context mContext;

    /**
     * It's called to create the adapter
     * @param context It gives the context from where it was called
     * @param resource
     * @param objects It gives to the adapter the ArrayList with the info from the movies
     */
    public ResultList_Adapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        myList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_adapter_results, parent, false);
        }

        // Views are created.
        TextView titleView = (TextView) convertView.findViewById(R.id.titleListItem);
        TextView directorView = (TextView) convertView.findViewById(R.id.directorListItem);
        TextView yearView = (TextView) convertView.findViewById(R.id.yearListItem);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageListItem);

        // The info is processed and prepared to be set in its different views.
        String[] myStringArray = myList.get(0).split("sep");

        // The Views are fed with the info they'll show.
        titleView.setText(myStringArray[0]);
        directorView.setText(myStringArray[1]);
        yearView.setText(myStringArray[2]);

        // The AsyncTask that is supposed to retrieve the image from the OMDb is called. The bitmap is set in the ImageView.
        GetTheImages_Asynk image = new GetTheImages_Asynk(imageView, 0);
        image.execute(myStringArray[3]);

        // The view is returned.
        return convertView;
    }

}


