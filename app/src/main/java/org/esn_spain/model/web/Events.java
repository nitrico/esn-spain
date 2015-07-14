package org.esn_spain.model.web;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class Events {

    @ElementList(required=false, entry="event", type=Event.class, inline=true)
    private List<Event> events;

    public List<Event> get() {
        return events;
    }

    public List<Event> getNext() {
        List<Event> list = new ArrayList<>();
        for (int i=0; i<4; i++) list.add(events.get(i));
        return list;
    }

    public List<Event> getPast() {
        List<Event> list = new ArrayList<>();
        for (int i=4; i<events.size(); i++) list.add(events.get(i));
        return list;
    }

}
