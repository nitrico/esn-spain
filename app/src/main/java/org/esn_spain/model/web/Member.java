package org.esn_spain.model.web;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="member")
public class Member {

    @Element public String member_name;
    @Element(required=false) public String member_image;
    @Element(required=false) public String member_text;
    //@Element public String url;

}
