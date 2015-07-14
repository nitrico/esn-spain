package org.esn_spain.model.simple;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root(name="social")
public class Social extends SimpleXmlObject {

  @ElementList(required=false, entry="profile", type=Profile.class, inline=true) public List<Profile> profiles;

}
