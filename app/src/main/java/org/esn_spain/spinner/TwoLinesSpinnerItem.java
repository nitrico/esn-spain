package org.esn_spain.spinner;

import java.io.Serializable;

public class TwoLinesSpinnerItem implements Serializable {

    private String mLine1;
    private String mLine2;
    private String mSectionId;

    public TwoLinesSpinnerItem(String line1, String line2, String sectionId) {
        mLine1 = line1;
        mLine2 = line2;
        mSectionId = sectionId;
    }

    public String getLine1() {
        return mLine1;
    }

    public String getLine2() {
        return mLine2;
    }

    public String getSectionId() {
        return mSectionId;
    }

}
