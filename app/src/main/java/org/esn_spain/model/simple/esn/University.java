package org.esn_spain.model.simple.esn;

import org.esn_spain.model.simple.SimpleXmlObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;

@Root(name="university")
public class University extends SimpleXmlObject implements Serializable {

    @Attribute
    private String name;

    @Attribute(required=false, name="name_en")
    private String nameEn;

    @Attribute(required=false, name="name_loc")
    private String nameLoc;

    @Element
    private String web;

    @Element(required = false)
    private String logo;


    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameLoc() {
        return nameLoc;
    }

    public String getWeb() {
        return web;
    }

    public String getLogo() {
        return logo;
    }

}
