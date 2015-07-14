package org.esn_spain.model.simple;

import org.esn_spain.model.simple.Event;
import org.esn_spain.model.simple.Member;
import org.esn_spain.model.simple.Office;
import org.esn_spain.model.simple.Partner;
import org.esn_spain.model.simple.Project;
import org.esn_spain.model.simple.SimpleXmlObject;
import org.esn_spain.model.simple.Social;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import java.util.ArrayList;
import java.util.List;

@Root
public class Data {

  @ElementListUnion({
      @ElementList(entry="office",  type=Office.class,  inline=true),
      @ElementList(entry="social",  type=Social.class,  inline=true),
      @ElementList(entry="event",   type=Event.class,   inline=true),
      @ElementList(entry="partner", type=Partner.class, inline=true),
      @ElementList(entry="project", type=Project.class, inline=true),
      @ElementList(entry="member",  type=Member.class,  inline=true)
  })
  private List<SimpleXmlObject> list = new ArrayList<>();

  public SimpleXmlObject get(int position) {
    return list.get(position);
  }

  public List<SimpleXmlObject> get() {
    return list;
  }

  public void setList(List<SimpleXmlObject> items) {
    list = items;
  }

}
