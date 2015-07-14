package org.esn_spain.model.simple;

public class Header {

  private static final int DEFAULT_COLOR = 0xFFAAAAAA; // 0xFFAAAAAA
  private String mName;
  private int mColor;
  private int mMargin;

  public Header(String name) {
    this(0, name, DEFAULT_COLOR);
  }

  public Header(String name, int color) {
    this(0, name, color);
  }

  public Header(int marginLeftDp, String name) {
    this(marginLeftDp, name, DEFAULT_COLOR);
  }

  public Header(int marginLeftDp, String name, int color) {
    mName = name;
    mColor = color;
    mMargin = marginLeftDp;
  }

  public String getName() {
    return mName;
  }

  public int getColor() {
    return mColor;
  }

  public int getMargin() {
    return mMargin;
  }

  @Override
  public String toString() {
    return mName;
  }

}
