package org.esn_spain.model.web;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="event")
public class Event {

    @Element public String event_name;
    @Element public String event_timestamp;
    @Element(required=false) public String event_image;
    @Element(required=false) public String event_date;
    @Element(required=false) public String event_time;
    @Element(required=false) public String event_place;
    @Element(required=false) public String event_price;
    @Element(required=false) public String event_link;
    @Element(required=false) public String event_body;
    //@Element public String url;

}
