package org.esn_spain.model.simple;

public class Space {

    private int mColor;

    public Space() {
        this(0x00FFFFFF);
    }

    public Space(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

}
