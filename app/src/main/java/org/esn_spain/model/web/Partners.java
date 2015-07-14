package org.esn_spain.model.web;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root
public class Partners {

    @ElementList(required=false, entry="partner", type=Partner.class, inline=true)
    private List<Partner> partners;

    public List<Partner> get() {
        return partners;
    }

}
