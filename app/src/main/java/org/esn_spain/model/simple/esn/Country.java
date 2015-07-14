package org.esn_spain.model.simple.esn;

import org.esn_spain.model.simple.SimpleXmlObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root(name="country")
public class Country extends SimpleXmlObject implements Serializable {

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

    @ElementList(required=false, entry="city", type=City.class, inline=true)
    private List<City> cities;


    public List<City> getCities() {
        return cities;
    }

    public City getCity(int position) {
        return cities.get(position);
    }

    public City getCity(String id) {
        for (City city : cities) if (city.getId().toUpperCase().equals(id.toUpperCase())) return city;
        return null;
    }

    public List<String> getCityNames() {
        List<String> list = new ArrayList<>();
        for (City city: cities) {
            list.add(city.getName());
        }
        return list;
    }

    public List<Section> getSections() {
        List<Section> sections = new ArrayList<>();
        for (City city : cities) sections.addAll(city.getSections());
        return sections;
    }

    public List<Section> getActiveSections() {
        List<Section> sections = new ArrayList<>();
        for (City city : cities)  {
            if (city.isActive()) {
                for (Section section : city.getSections()) {
                    if (section.isActive()) sections.add(section);
                }
            }
        }
        return sections;
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
        for (City city : cities) {
            if (city.findSectionById(iden) != null) return city.findSectionById(iden);
        }
        return null;
    }

}

