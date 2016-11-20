package com.custardgames.worldtrotter.managers;

import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EventManager {
    private static EventManager _instance = new EventManager();
    private Map<Class<?>, LinkedList<EventListener>> listeners;

    public EventManager() {
        listeners = new HashMap<Class<?>, LinkedList<EventListener>>();
    }

    public static EventManager get_instance() {
        return _instance;
    }

    public void register(Class<?> eventType, EventListener listener) {
        if (!listeners.containsKey(eventType)) {
            listeners.put(eventType, new LinkedList<EventListener>());
        }

        listeners.get(eventType).add(listener);
    }

    public void broadcast(Object event) {
        LinkedList<EventListener> currentListeners = listeners.get(event.getClass());
        if (currentListeners != null) {
            for (EventListener l : currentListeners) {
                Method m = findMethod(l, event.getClass());
                try {
                    m.invoke(l, event);
                } catch (Exception e) {
                }
            }
        }
    }

    private Method findMethod(EventListener listener, Class<?> eventType) {
        Method[] methods = listener.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("handle")) {
                Class<?>[] params = m.getParameterTypes();
                if (params.length == 1 && params[0] == eventType) {
                    return m;
                }
            }
        }
        return null;
    }
}
