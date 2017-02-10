package com.maangata.l.omdbapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by l on 9/2/17.
 */
public class GetTheImages_Asynk extends AsyncTask<String, Void, Bitmap> {

    ImageView fromTheFragment;
    int whereItComesFrom;

    /**
     * Creates the constructor for the the AsyncTask.
     * @param mImageView It gives me the ImageView where the Bitmap retrieved will be shown.
     * @param whereItComesFrom It's a number used by the app as a code to know the exact place where the AsyncTask was launched, and therefore what it has to do with the Bitmap it retrieves.
     */
    public GetTheImages_Asynk(ImageView mImageView, int whereItComesFrom) {
        this.fromTheFragment = mImageView;
        this.whereItComesFrom = whereItComesFrom;
    }

    @Override
    protected Bitmap doInBackground(String... url) {

        HttpURLConnection urlConnection = null;

        try {
            // Setting up the connection with OMDb to retrieve the image.
            URL url1 = new URL(url[0]);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Creating the bitmap with the data retrieved.
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            // Disconnecting.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // If the AsyncTask was thrown from the Adapter, to show it in the LIstView, create a Thumbnail with the built-in tools and show it.
        if (whereItComesFrom == 0) {
            // It comes from the Adapter.
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth()/2, bitmap.getWidth()/2);
            fromTheFragment.setImageBitmap(bitmap);

            // If it was called from the Details_Activity, scale it making it bigger and display it in the ImageView.
        } else if (whereItComesFrom == 1) {
            // It comes from Details.
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*3, bitmap.getHeight()*3, true);
            fromTheFragment.setImageBitmap(bitmap);
        }
    }
}
