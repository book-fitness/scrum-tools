package net.scrumtools.entity;

public class RoomTimer {
    private long totalTime = 0;
    private long startTime;
    private boolean running;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
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
}
