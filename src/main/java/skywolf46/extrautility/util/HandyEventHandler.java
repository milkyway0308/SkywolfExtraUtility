package skywolf46.extrautility.util;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandyEventHandler {
    private final HashMap<Class<? extends Event>, HandyEventListener> handyEvent = new HashMap<>();
    private JavaPlugin pl;

    public HandyEventHandler(JavaPlugin pl) {
        this.pl = pl;
    }

    public void registerHandler(Class<?> cl) {
        for (Method mtd : cl.getMethods()) {
            if (Modifier.isStatic(mtd.getModifiers())) {
                mtd.setAccessible(true);
                if (mtd.getAnnotation(EventHandler.class) != null) {
                    registerHandler(mtd);
                }
            } else {
                if (mtd.getAnnotation(EventHandler.class) != null) {
                    System.err.println("Caution: EventHandler detected on non-static method \"" + mtd.getName() + "\" in class " + cl.getName());
                }
            }
        }
    }

    private void registerHandler(Method mtd) {
        HandyEventInvoker inv = new HandyEventInvoker(mtd);
        if (inv.getBaseEvent() == null)
            return;
        handyEvent.computeIfAbsent(inv.getBaseEvent(), a -> new HandyEventListener()).register(inv);
    }

    private class HandyEventListener implements Listener {
        private final HashMap<EventPriority, List<HandyEventInvoker>> listeners = new HashMap<>();
        private final List<EventPriority> registered = new ArrayList<>();

        public void register(HandyEventInvoker invoker) {
            if (!registered.contains(invoker.getPriority())) {
                registered.add(invoker.getPriority());
                Bukkit.getPluginManager().registerEvent(invoker.getBaseEvent(), this, invoker.getPriority(), new EventExecutor() {
                    @Override
                    public void execute(Listener listener, Event event) throws EventException {
                        listenEvent(invoker.getPriority(), event);
                    }
                }, pl);
            }
            listeners.computeIfAbsent(invoker.getPriority(), a -> new ArrayList<>()).add(invoker);
        }

        public void listenEvent(EventPriority pr, Event ev) {
            if (listeners.containsKey(pr))
                listeners.get(pr)
                        .forEach(inv -> inv.onEvent(ev));

        }
    }

    private static class HandyEventInvoker {
        private final Class<? extends Event> baseEvent;
        private final Method methodWorker;
        private final EventPriority priority;

        public HandyEventInvoker(Method mtd) {
            EventHandler handler = mtd.getAnnotation(EventHandler.class);
            this.priority = handler.priority();
            baseEvent = mtd.getParameters().length > 0 ?
                    (Event.class.isAssignableFrom(mtd.getParameters()[0].getType()) ? (Class<? extends Event>) mtd.getParameters()[0].getType() : null)
                    : null;
            this.methodWorker = mtd;
        }

        public Class<? extends Event> getBaseEvent() {
            return baseEvent;
        }

        public EventPriority getPriority() {
            return priority;
        }

        public void onEvent(Event ex) {
            Object[] params = new Object[methodWorker.getParameters().length];
            params[0] = ex;
            try {
                methodWorker.invoke(null, params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
