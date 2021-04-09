package me.hypnos.Core;

public class Stopwatch {

    private long startTime, stopTime = 0;
    private boolean running = false;

    public void start(){
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop(){
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getElapsedSeconds(){
        long time;
        if (running){
            time = ((System.currentTimeMillis() - startTime) / 1000);
        } else {
            time = ((stopTime - startTime) / 1000);
        }
        return time;
    }

}
