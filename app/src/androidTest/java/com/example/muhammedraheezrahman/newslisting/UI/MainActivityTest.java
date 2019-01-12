package com.example.muhammedraheezrahman.newslisting.UI;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.muhammedraheezrahman.newslisting.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        View v = mainActivity.findViewById(R.id.recyclerview);
        assertNotNull(v);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}