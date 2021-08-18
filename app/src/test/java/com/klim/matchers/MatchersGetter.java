package com.klim.matchers;

import org.hamcrest.Matcher;

public class MatchersGetter {

    public static Matcher<String> isShorterThan(int limit) {
        return new ShorterStringMatcher(limit);
    }

    public static Matcher<String> isLongerThan(int limit) {
        return new LongerStringMatcher(limit);
    }

}