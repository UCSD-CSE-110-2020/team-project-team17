package edu.ucsd.cse110.walkwalkrevolution.fitness;

public class Steps {
    private long dailyTotal;
    private long latest;

    public Steps(){
        this(0,0);
    }

    public Steps(long dailyTotal, long latest) {
        this.dailyTotal = dailyTotal;
        this.latest = latest;
    }

    public void setDailyTotal(long dailyTotal) {
        this.dailyTotal = dailyTotal;
    }

    public void setLatest(long latest) {
        this.latest = latest;
    }

    public long getDailyTotal() {
        return dailyTotal;
    }

    public long getLatest() {
        return latest;
    }
}
