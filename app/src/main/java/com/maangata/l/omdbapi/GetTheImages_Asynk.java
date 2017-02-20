package com.maangata.l.omdbapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by l on 9/2/17.
 */
public class GetTheImages_Asynk extends AsyncTask<String, Void, Bitmap> {

    private ImageView fromTheFragment;
    private int whereItComesFrom;
    private String keyForTheCache;
    private LruCache<String, Bitmap> mMemoryCache;
    private final int FROM_FRAGMENT = 000;
    private final int FROM_DETAILS = 111;

    /**
     * Creates the constructor for the the AsyncTask.
     * @param mImageView It gives me the ImageView where the Bitmap retrieved will be shown.
     * @param whereItComesFrom It's a number used by the app as a code to know the exact place where the AsyncTask was launched, and therefore what it has to do with the Bitmap it retrieves.
     */
    public GetTheImages_Asynk(ImageView mImageView, int whereItComesFrom) {
        this.fromTheFragment = mImageView;
        this.whereItComesFrom = whereItComesFrom;
    }

    /**
     * Creates de constructor for the Async_Task when it's called from the ArrayAdapter. It needs to cache the bitmaps to produce a
     * a smoother Listiew scroll, so it needs more parameters.
     * @param mImageView t gives me the ImageView where the Bitmap retrieved will be shown.
     * @param whereItComesFrom It's a number used by the app as a code to know the exact place where the AsyncTask was launched, and therefore what it has to do with the Bitmap it retrieves.
     * @param keyForTheCache It gives the cache key that will have every image cached.
     * @param mMemoryCache The Lrucache object.
     */
    public GetTheImages_Asynk(ImageView mImageView, int whereItComesFrom, String keyForTheCache, LruCache<String, Bitmap> mMemoryCache) {
        this.fromTheFragment = mImageView;
        this.whereItComesFrom = whereItComesFrom;
        this.keyForTheCache = keyForTheCache;
        this.mMemoryCache = mMemoryCache;
    }

    /**
     * Adds the bitmap to the cache.
     * @param key The name for the cache image.
     * @param bitmap The image it's being cached.
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
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
                // If it was called from the fragment, cache the bitmaps. It cahces it in the size it'll be shown in the fragment.
                if (whereItComesFrom == FROM_FRAGMENT) {
                    addBitmapToMemoryCache(keyForTheCache, ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / 2, bitmap.getWidth() / 2));
                }
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
        if (whereItComesFrom == FROM_FRAGMENT) {
            // It comes from the Adapter.
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth()/2, bitmap.getWidth()/2);
            fromTheFragment.setImageBitmap(bitmap);

            // If it was called from the Details_Activity, scale it making it bigger and display it in the ImageView.
        } else if (whereItComesFrom == FROM_DETAILS) {
            // It comes from Details.
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*3, bitmap.getHeight()*3, true);
            fromTheFragment.setImageBitmap(bitmap);
        }
    }
}
