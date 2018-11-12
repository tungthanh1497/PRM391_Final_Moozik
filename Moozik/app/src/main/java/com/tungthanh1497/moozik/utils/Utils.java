package com.tungthanh1497.moozik.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac-vuongvu on 7/29/17.
 */

public class Utils {
    public  static String convertTime(int time) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }
}
