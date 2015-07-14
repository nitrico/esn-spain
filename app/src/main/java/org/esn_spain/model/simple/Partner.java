package org.esn_spain.model.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root(name="partner")
public class Partner extends SimpleXmlObject {

  @Element public String name;
  @Element public String image;
  @Element(required=false) public String link;
  @Element(required=false) public String text;
  @Element(required=false) public String color;
  @Element(required=false) public String address;
  @Element(required=false) public String searchmap;
  @Element(required=false) public double lat, lng;
  @ElementList(required=false, entry="offer", type=String.class, inline=true) public List<String> offers;

  private boolean expanded;

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean isExpanded) {
    expanded = isExpanded;
  }

}
