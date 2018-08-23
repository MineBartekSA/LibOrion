package com.minebarteksa.orion.events;

import java.util.ArrayList;
import java.util.List;

public class OrionEvent
{
    private List<IOrionListener> listeners = new ArrayList<>();

    public void invokeListeners()
    {
        for(IOrionListener l : listeners)
            l.onOrionEvent(this);
    }

    /**
     * Best to implement in onCreated(), but you can do one in onUpdate()
     */
    public void addListener(IOrionListener listener) { if(!listeners.contains(listener)) listeners.add(listener); }

    public void removeListener(IOrionListener listener) { if(listeners.contains(listener)) listeners.remove(listener); }
}
