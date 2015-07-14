package org.esn_spain.model.web;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root
public class Members {

    @ElementList(required=false, entry="member", type=Member.class, inline=true)
    private List<Member> members;

    public List<Member> get() {
        return members;
    }

}
