package sharma.com.androidjokelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AndroidJokes extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_layout);

        TextView jokeTextView = (TextView) findViewById(R.id.joke);

        Intent intent = getIntent();
        String joke = intent.getStringExtra("JOKE");
        jokeTextView.setText(joke);

    }
}
