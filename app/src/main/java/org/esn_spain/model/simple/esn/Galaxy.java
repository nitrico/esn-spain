package org.esn_spain.model.simple.esn;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Root
public class Galaxy implements Serializable {

    @ElementList(required=false, entry="country",  type=Country.class,  inline=true)
    private List<Country> galaxy = new ArrayList<>();

    public List<Country> getCountries() {
        return galaxy;
    }

    public Country getCountry(int position) {
        return galaxy.get(position);
    }

    public Country getCountryById(String id) {
        for (Country country : galaxy) if (id.toUpperCase().equals(country.getId().toUpperCase())) return country;
        return null;
    }

    public List<String> getCountryNames() {
        List<String> list = new ArrayList<>();
        for (Country country: galaxy) {
            list.add(country.getName());
        }
        return list;
    }

    public Section findSectionById(String iden) {
        for (Country country : galaxy) {
            if (country.findSectionById(iden) != null) return country.findSectionById(iden);
        }
        return null;
    }

}
