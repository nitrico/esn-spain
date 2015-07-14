package org.esn_spain.model.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
import java.io.Serializable;

@Root(name="item")
public class Item extends SimpleXmlObject implements Serializable {

  @Attribute public int type;
  @Attribute(required=false) public int left;
  @Attribute(required=false) public int right;
  @Attribute(required=false) public int top;
  @Attribute(required=false) public int bottom;
  @Text public String content;

}
