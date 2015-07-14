package org.esn_spain.model.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
import java.io.Serializable;

@Root(name="profile")
public class Profile extends SimpleXmlObject implements Serializable {

  @Attribute public String name;
  @Attribute(required=false) public String color;
  @Attribute(required=false) public String logo;
  @Attribute(required=false) public String uri;
  @Text public String content;

}
