package com.example.muhammedraheezrahman.newslisting.UI;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.muhammedraheezrahman.newslisting.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsDetailActivityTest {

    @Rule
    public ActivityTestRule<NewsDetailActivity> activityTestRule = new ActivityTestRule<NewsDetailActivity>(NewsDetailActivity.class);
    private NewsDetailActivity detailActivity;

    @Before
    public void setUp() throws Exception {
        detailActivity = activityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = detailActivity.findViewById(R.id.cardview);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        detailActivity = null;
    }
}