package org.esn_spain.model;

import java.io.Serializable;

public class SectionItem implements Serializable {

    private String name;
    private String university;
    private String url;

    public SectionItem(String sectionName, String universityName) {
        name = sectionName;
        university = universityName;
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public String getUrl() {
        return url;
    }

}
