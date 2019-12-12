package com.example.app;

import android.content.Context;

import org.junit.Test;

import androidx.test.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented notifyAdapter, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under notifyAdapter.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.app", appContext.getPackageName());
    }
}
