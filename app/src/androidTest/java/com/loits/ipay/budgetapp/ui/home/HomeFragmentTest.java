package com.loits.ipay.budgetapp.ui.home;

import androidx.test.rule.ActivityTestRule;

import com.loits.ipay.budgetapp.MainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class HomeFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    MainActivity mainActivity = null;
    HomeFragment homeFragment = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
        homeFragment = new HomeFragment();
    }


    @Test
    public void testMainActivity(){
        Assert.assertNotNull(mainActivity);
    }


    @After
    public void tearDown() throws Exception {

        mainActivity = null;
        homeFragment = null;
    }

}