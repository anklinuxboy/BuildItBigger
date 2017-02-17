package sharma.com.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.JavaJokes;
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
        new EndpointsAsyncTask().execute(this);
    }
}

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-158813.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
        context = params[0];
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        JavaJokes javaJokes = new JavaJokes();
        Intent intent = new Intent(context, AndroidJokes.class);
        intent.putExtra("JOKE", javaJokes.getJoke());
        context.startActivity(intent);
    }
}
