package edu.haverford.cs.squirrelfacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SquirrelInfoActivity extends AppCompatActivity {
    private ImageView mSquirrelPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squirrel_info);
        Intent i = getIntent();
        TextView name = (TextView) findViewById(R.id.squirrelName);
        TextView location = (TextView) findViewById(R.id.squirrelLocation);
        mSquirrelPic = (ImageView)findViewById(R.id.squirrelPic);
        name.setText(i.getStringExtra("name"));
        location.setText(i.getStringExtra("location"));
        new SquirrelImageLoader().execute(i.getStringExtra("picture"));
    }

    private class SquirrelImageLoader extends AsyncTask<String, Void, Bitmap> {
        Bitmap mBitmap;

        /**
         *
         * @param strings
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            InputStream stream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
                mBitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        /**
         * TODO: Implement this
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            //The black squirrel image will act as the placeholder until the image is loaded.
            mSquirrelPic.setImageResource(R.mipmap.squirrel);
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //If the background operation successful fetches a legal mitmap image will be loaded. If not, the placeholder
            // image set in onProgressUpdate will persist.
            if (bitmap != null) {
                mSquirrelPic.setImageBitmap(bitmap);
            }
        }
    }
}
