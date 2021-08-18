package com.klim.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ShorterStringMatcher extends TypeSafeMatcher<String> {

    private int requiredLength;

    public ShorterStringMatcher(int requiredLength) {
        this.requiredLength = requiredLength;
    }

    @Override
    protected boolean matchesSafely(String s) {
        return s.length() <= requiredLength;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the string being checked is longer than " + requiredLength);
    }
}
