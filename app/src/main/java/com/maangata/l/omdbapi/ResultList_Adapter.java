package com.maangata.l.omdbapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
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
    public final int FROM_ADAPTER = 000;

    private LruCache<String, Bitmap> mMemoryCache;


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

        View listItem = convertView;
        ViewHolder viewHolder = null;
        String[] myStringArray = myList.get(position).split("sep");

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listItem = inflater.inflate(R.layout.item_adapter_results, parent, false);

        viewHolder = new ViewHolder(listItem);
        listItem.setTag(viewHolder);

        // The Views are fed with the info they'll show.
        viewHolder.titleView.setText(myStringArray[0]);
        // directorView.setText(myStringArray[1]);
        viewHolder.yearView.setText(myStringArray[1]);

        // The AsyncTask that is supposed to retrieve the image from the OMDb is called. The bitmap is set in the ImageView.
        GetTheImages_Asynk image = new GetTheImages_Asynk(viewHolder.imageView, FROM_ADAPTER);
        image.execute(myStringArray[3]);

        // The view is returned.
        return listItem;
    }


    public static class ViewHolder {
        public final TextView titleView;
        public final TextView yearView;
        public final ImageView imageView;

        public ViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.titleListItem);
            yearView = (TextView) view.findViewById(R.id.yearListItem);
            imageView = (ImageView) view.findViewById(R.id.imageListItem);

        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}


