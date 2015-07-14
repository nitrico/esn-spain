package org.esn_spain.model.web;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="partner")
public class Partner {

    @Element public String partner_name;
    @Element(required=false) public String partner_image;
    @Element(required=false) public String partner_offers;
    @Element(required=false) public String partner_address;
    @Element(required=false) public String partner_link;

}
