package com.klim.matchers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class LongerStringMatcher(
    private val requiredLength: Int
) : TypeSafeMatcher<String>() {

    override fun matchesSafely(s: String): Boolean = s.length >= requiredLength

    override fun describeTo(description: Description) {
        description.appendText("the string being checked is shorter than $requiredLength")
    }
}