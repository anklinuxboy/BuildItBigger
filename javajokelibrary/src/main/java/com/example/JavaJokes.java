package com.example;

import java.util.ArrayList;
import java.util.List;

public class JavaJokes {

    private static final List<String> jokes = new ArrayList<String>() {{
        add("Your python drank my Java");
        add("There are only 10 types of people in the world: those that understand binary and those that don’t.");
        add("Artificial intelligence usually beats real stupidity.");
        add("CAPS LOCK – Preventing Login Since 1980.");
        add("The truth is out there. Anybody got the URL?");
        add("Unix is user friendly. It’s just selective about who its friends are.");
        add("Failure is not an option. It comes bundled with your Microsoft product.");
    }};

    public String getJoke() {
        int size = jokes.size();
        return jokes.get((int) (Math.random() * (size-1)));
    }
}
