package xyz.starsoc.core.impl;

public interface GameRunnerImpl extends Runnable{
    void init();

    void start();

    void stop();

    void destroy();


}
