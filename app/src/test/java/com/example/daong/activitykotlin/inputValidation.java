package com.example.daong.activitykotlin;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class inputValidation {

    private MainActivity mActivity = new MainActivity();

    @Test
    public void validInput_ReturnsTrue() {
        assertThat(mActivity.isValidInput("myinput"), is(true));
    }

    @Test
    public void emptyInput_ReturnsFalse() {
        assertThat(mActivity.isValidInput(""), is(false));
    }

}
