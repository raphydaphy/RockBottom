package de.ellpeck.rockbottom.apiimpl;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.IEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandler implements IEventHandler{

    private final Map<Class<? extends Event>, List<IEventListener>> registry = new HashMap<>();

    @Override
    public <T extends Event> void registerListener(Class<T> type, IEventListener<T> listener){
        List<IEventListener> listeners = this.registry.computeIfAbsent(type, k -> new ArrayList<>());
        listeners.add(listener);

        RockBottomAPI.logger().info("Registered event listener "+listener+" for event "+type);
    }

    @Override
    public <T extends Event> void unregisterListener(Class<T> type, IEventListener<T> listener){
        List<IEventListener> listeners = this.registry.get(type);

        if(listeners != null && listeners.contains(listener)){
            listeners.remove(listener);

            if(listeners.isEmpty()){
                this.registry.remove(type);
            }

            RockBottomAPI.logger().info("Unregistered event listener "+listener+" for event "+type);
        }
        else{
            RockBottomAPI.logger().warning("Couldn't unregister event listener "+listener+" for event "+type+" as it wasn't registered");
        }
    }

    @Override
    public void unregisterAllListeners(Class<? extends Event> type){
        if(this.registry.containsKey(type)){
            this.registry.remove(type);

            RockBottomAPI.logger().info("Unregistered all listeners for event "+type);
        }
        else{
            RockBottomAPI.logger().warning("Couldn't unregister all events for type "+type+" as there were none registered");
        }
    }

    @Override
    public EventResult fireEvent(Event event){
        EventResult result = EventResult.DEFAULT;

        List<IEventListener> listeners = this.registry.get(event.getClass());
        if(listeners != null && !listeners.isEmpty()){
            for(IEventListener listener : listeners){
                result = listener.listen(result, event);

                if(result.shouldCancel()){
                    break;
                }
            }
        }

        return result;
    }
}
