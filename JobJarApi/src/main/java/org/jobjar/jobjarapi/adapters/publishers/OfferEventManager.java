package org.jobjar.jobjarapi.adapters.publishers;


import org.jobjar.jobjarapi.adapters.listeners.EventListener;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferEventManager {
    Map<OfferCreateDto, List<EventListener>> listeners = new HashMap<>();

    public OfferEventManager(OfferCreateDto... offers) {
        for (var offer : offers) {
            listeners.put(offer, new ArrayList<>());
        }
    }

    public void subscribe(OfferCreateDto offer, EventListener listener) {
        var events = listeners.get(offer);
        if (events != null) {
            events.add(listener);
        }
    }

    public void unsubscribe(OfferCreateDto offer, EventListener listener) {
        var events = listeners.get(offer);
        if (events != null) {
            events.remove(listener);
        }
    }

    public void notify(OfferCreateDto offer) {
        var events = listeners.get(offer);
        if (events != null) {
            events.forEach(x -> x.update(offer));
        }
    }

    public void notifyAllListeners() {
        listeners
                .forEach((x, y) -> y
                        .forEach( z -> {
                            // TODO: Unsubscribe after notification
                            z.update(x);
                        }));
    }
}
