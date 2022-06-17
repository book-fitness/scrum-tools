package net.scrumtools.entity;

public class RoomTimer {
    private long totalTime = 0;
    private long startTime;
    private boolean running;
    private long timerLimit;

    public void start() {
        if (running) return;
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        if (!running) return;
        long stopTime = System.currentTimeMillis();
        totalTime += stopTime - startTime;
        running = false;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public boolean isRunning() {
        return running;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setTimerLimit(long timerLimit) {
        this.timerLimit = timerLimit;
    }

    public long getTimerLimit() {
        return timerLimit;
    }
}
