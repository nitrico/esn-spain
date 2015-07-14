package org.esn_spain.model.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.List;

@Root(name="project")
public class Project extends SimpleXmlObject implements Serializable {

  @Element public String name;
  @Element public String image;
  @Element public String from;
  @Attribute public String color;
  @Attribute public boolean inprojects;
  @Attribute public boolean promoted;
  @Attribute public boolean activities;
  @Attribute public boolean activitiesfirst;
  @Element(required=false) public String link;
  @Element(required=false) public String promotedimage;
  @Element(required=false) public String promotedsubtitle;
  @ElementList(required=false, entry="screen", type=Screen.class, inline=true) public List<Screen> screens;

}
