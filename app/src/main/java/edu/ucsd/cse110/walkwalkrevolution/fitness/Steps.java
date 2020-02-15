package edu.ucsd.cse110.walkwalkrevolution.fitness;

import android.util.Log;

public class Steps {
    private static final String TAG = "Steps";
    private long previousDailyTotal;
    private long dailyTotal;
    private long latest;

    public Steps(){
        this(0);
    }

    public Steps(long dailyTotal) {
        this.dailyTotal = dailyTotal;
        this.previousDailyTotal = dailyTotal;
        this.latest = 0;
    }

    public void updateStats(long dailyTotal) {
        previousDailyTotal = this.dailyTotal;
        this.dailyTotal = dailyTotal;

        updateLatest();
    }

    private void updateLatest(){
        Log.d(TAG, "updateLatest: Entering");

        if(dailyTotal > previousDailyTotal) {
            latest = dailyTotal - previousDailyTotal;
        } else {
            latest = dailyTotal;
        }
        Log.d(TAG, "updateLatest: Updated Walk Specific stats!");
        Log.d(TAG, "updateLatest: Current total " + previousDailyTotal);
        Log.d(TAG, "updateLatest: New total " + dailyTotal);
        Log.d(TAG, "updateLatest: latest " + latest);
    }

    public long getDailyTotal() {
        return dailyTotal;
    }

    public long getLatest() {
        return latest;
    }
}
