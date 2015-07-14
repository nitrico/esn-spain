package org.esn_spain.model.simple.esn;

import org.esn_spain.model.simple.SimpleXmlObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.List;

@Root(name="section")
public class Section extends SimpleXmlObject implements Serializable {

    @Attribute
    private String id;

    @Attribute(name="city_id")
    private String cityId;

    @Attribute(name="country_id")
    private String countryId;

    @Attribute(required=false)
    private boolean active;

    @Element
    private String name;

    @Element(required=false)
    private String photo;

    @Element
    private University university;

    @ElementList(entry="file", type=String.class, inline=true)
    private List<String> files;


    public String getId() {
        return id;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public University getUniversity() {
        return university;
    }

    public String getPhoto() {
        return photo;
    }

    public boolean isActive() {
        return active;
    }

    public String getFile() {
        return files.get(0);
    }

    public List<String> getFiles() {
        return files;
    }


}
