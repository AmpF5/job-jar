package org.jobjar.muncher.utils;

public abstract class TimeConverter {
    /**
     * @param start Start in nanosecond {@code System.nanoTime()}
     * @param end   End in nanosecond {@code System.nanoTime()}
     * @return Elapsed time in milliseconds
     */
    public static double getElapsedTime(long start, long end) {
        return (end - start) / 1_000_000.0;
    }

}
