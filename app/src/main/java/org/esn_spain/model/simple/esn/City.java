package org.esn_spain.model.simple.esn;

import org.esn_spain.model.simple.SimpleXmlObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.List;

@Root(name="city")
public class City extends SimpleXmlObject implements Serializable {

    @Attribute
    private String id;

    @Attribute
    private String name;

    @Attribute(required=false, name="name_en")
    private String nameEn;

    @Attribute(required=false, name="name_loc")
    private String nameLoc;

    @Attribute(required=false)
    private boolean active;

    @ElementList(required=false, entry="section", type=Section.class, inline=true)
    private List<Section> sections;


    public List<Section> getSections() {
        return sections;
    }

    public Section getSection(int position) {
        return sections.get(position);
    }

    public Section getSection(String id) {
        for (Section section : sections) {
            if (section.getId().toUpperCase().equals(id.toUpperCase())) {
                return section;
            }
        }
        return null;
    }

    public boolean isActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameLoc() {
        return nameLoc;
    }

    public Section findSectionById(String iden) {
        for (Section section : sections) {
            if (section.getId().toUpperCase().equals(iden.toUpperCase())) return section;
        }
        return null;
    }

}
