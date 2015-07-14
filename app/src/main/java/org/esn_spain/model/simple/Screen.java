package org.esn_spain.model.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.List;

@Root(name="screen")
public class Screen extends SimpleXmlObject implements Serializable {

  @Attribute public String name;
  @Attribute(required=false) public String color;
  @ElementList(required=false, entry="item", type=Item.class, inline=true) public List<Item> items;

}
