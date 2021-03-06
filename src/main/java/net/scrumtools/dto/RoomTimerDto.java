package net.scrumtools.dto;

import net.scrumtools.entity.RoomTimer;

public class RoomTimerDto {
    private long totalTime;
    private long startTime;
    private boolean running;
    private long timerLimit;

    public RoomTimerDto(RoomTimer roomTimer) {
        this.totalTime = roomTimer.getTotalTime();
        this.startTime = roomTimer.getStartTime();
        this.running = roomTimer.isRunning();
        this.timerLimit = roomTimer.getTimerLimit();
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getTimerLimit() {
        return timerLimit;
    }

    public void setTimerLimit(long timerLimit) {
        this.timerLimit = timerLimit;
    }
}
