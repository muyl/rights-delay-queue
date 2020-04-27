package com.rights.delay.common.util;

/**
 * Created by Xs.Tao on 2017/7/21.
 */
public final class BlockUtils {

    /**
     * DEF_SLEEP_TIMES
     */
    public static final long DEF_SLEEP_TIMES = 100L;

    /**
     * Waiting short time
     */
    public static void waitingShortTime() {
        sleep(DEF_SLEEP_TIMES);
    }

    /**
     * Sleep *
     *
     * @param millis millis
     * @param isInterrupt is interrupt
     */
    public static void sleep(final long millis, final boolean isInterrupt) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ex) {
            if (isInterrupt) {
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * Sleep *
     *
     * @param millis millis
     */
    public static void sleep(final long millis) {
        sleep(millis, false);
    }
}
