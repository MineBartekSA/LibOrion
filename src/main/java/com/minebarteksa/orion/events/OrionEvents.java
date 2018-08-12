package com.minebarteksa.orion.events;

import java.util.ArrayList;
import java.util.List;

public class OrionEvents
{
    private List<OrionListener> listeners = new ArrayList<>();

    public void invokeListeners()
    {
        for(OrionListener l : listeners)
            l.onOrionEvent(this);
    }

    /**
     * Best to implement in onCreated(), but you can do one in onUpdate()
     */
    public void addListener(OrionListener listener) { listeners.add(listener); }

    public void removeListener(OrionListener listener) { listeners.remove(listener); }
}
