package org.esn_spain.model.simple;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;

@Root(name="offer")
public class Offer extends SimpleXmlObject implements Serializable {

  @Element public String offer;

}
