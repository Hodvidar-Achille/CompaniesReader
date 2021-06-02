package com.hodvidar.cr.utils.numeric;

public final class MillisecondFormatter {
    private static final long ONE_HOUR = 3600000;
    private static final long ONE_MINUTE = 60000;
    private static final long ONE_SECOND = 1000;

    private MillisecondFormatter() {
        throw new IllegalStateException("Utility class");
    }

    public static final String asTime(long milliseconds) {
        String time = "";
        final long hour = Math.floorDiv(milliseconds, ONE_HOUR);
        time += hour + " hour(s) ";
        milliseconds -= ONE_HOUR * hour;
        final long minute = Math.floorDiv(milliseconds, ONE_MINUTE);
        time += minute + " minute(s) ";
        milliseconds -= ONE_MINUTE * minute;
        final long second = Math.floorDiv(milliseconds, ONE_SECOND);
        time += second + " second(s) ";
        milliseconds -= ONE_SECOND * second;
        time += milliseconds + " ms";
        return time;
    }
}
