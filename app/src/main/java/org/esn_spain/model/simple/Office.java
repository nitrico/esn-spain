package org.esn_spain.model.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="office")
public class Office extends SimpleXmlObject{

  @Element public String address;
  @Element public String open;
  @Element(required=false) public String image;
  @Element(required=false) public String label;
  @Element(required=false) public double lat;
  @Element(required=false) public double lng;
  @Element(required=false) public int zoom;

}
