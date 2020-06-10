package com.ljs.learn.pattern.observer.improve;


public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
