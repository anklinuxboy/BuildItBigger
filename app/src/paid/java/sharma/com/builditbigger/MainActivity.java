package sharma.com.builditbigger;

import android.content.Intent;
import android.os.Bundle;

import com.example.JavaJokes;

import butterknife.OnClick;
import sharma.com.androidjokelibrary.AndroidJokes;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.joke_button)
    public void jokeButtonClicked() {
        JavaJokes javaJokes = new JavaJokes();
        Intent intent = new Intent(this, AndroidJokes.class);
        intent.putExtra("JOKE", javaJokes.getJoke());
        startActivity(intent);
    }
}
