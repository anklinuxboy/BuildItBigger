package sharma.com.builditbigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ankitsharma.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sharma.com.androidjokelibrary.AndroidJokes;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutID) {
        super.setContentView(layoutID);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.joke_button)
    public void jokeButtonClicked() {
        if (networkConnected()) {
            EndpointsAsyncTask asyncTask = new EndpointsAsyncTask(this);
            asyncTask.execute();
        } else {
            Toast.makeText(this, getString(R.string.network_not_available), Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public boolean networkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return (activeNetwork != null) && (activeNetwork.isConnected());
    }
}

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context activity;

    private ProgressDialog dialog;

    public EndpointsAsyncTask(Activity activity) {
        super();
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setMessage(activity.getString(R.string.progress_bar_title));
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-158813.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(activity, AndroidJokes.class);
        intent.putExtra("JOKE", result);
        activity.startActivity(intent);
    }
}
