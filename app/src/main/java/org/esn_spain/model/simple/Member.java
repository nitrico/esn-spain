package org.esn_spain.model.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;

@Root(name="member")
public class Member extends SimpleXmlObject implements Serializable {

  @Element public String name;
  @Element public String image;
  @Element public String title;
  @Element(required=false) public String fb;

}
