package fr.enderstevegamer.satellitary.timers;

public class Clock implements Timer {
    private double startTime;
    private boolean isStarted;

    public Clock(boolean startImmediately) {
        this.startTime = System.currentTimeMillis();
        this.isStarted = startImmediately;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {
        this.isStarted = false;
    }
}
