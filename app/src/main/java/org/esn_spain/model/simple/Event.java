package org.esn_spain.model.simple;

import android.support.annotation.NonNull;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Root(name="event")
public class Event extends SimpleXmlObject implements
        Comparable<Event>,
        Serializable {

  @Element public String name;
  @Element public String image;
  @Element public String link;
  @Element public String date;

  @Attribute(required=false) public boolean hiddentitle;
  @Attribute(required=false) public boolean big;
  @Element(required=false) public boolean subtitle;

  @Element(required=false) public String dateend;
  @Element(required=false) public String datestring;
  @Element(required=false) public String time;
  @Element(required=false) public String timeend;
  @Element(required=false) public String timestring;

  @Element(required=false) public String place;
  @Element(required=false) public String address;
  @Element(required=false) public String point;

  @Element(required=false) public String price;
  @Element(required=false) public String extra;

  @Element(required=false) public String color;
  @Element(required=false) public String fb;
  @Element(required=false) public String album;
  @Element(required=false) public String signup;
  @Element(required=false) public String text;
  @Element(required=false) public String project;

  @ElementList(required=false, entry="include", type=String.class, inline=true) public List<String> includes;
  @ElementList(required=false, entry="photo",   type=String.class, inline=true) public List<String> photos;
  @ElementList(required=false, entry="tag",     type=String.class, inline=true) public List<String> tags;

  public String getDate() {
    if (isToday()) return "HOY " +getDayName(getWeekDay()) +" " +getDayInt();
    else if (isTomorrow()) return "Mañana " +getDayName(getWeekDay()) +" " +getDayInt();
    else if (isThisMonth() && !isPast()) return "" +getDayName(getWeekDay()) +" " +getDayInt();
    else /*if (!isPast())*/
      return "" /*+getDayName(getWeekDay()) +" "*/ +getDayInt() +" de " +getMonthName(getMonth());
    //return date;
  }

  public String getDatePast() {
    return "" +getDayInt() +" de " +getMonthName(getMonth());
  }

  public String getDay() {
    String s = date.substring(0,2);
    if (s.charAt(0)=='0') return ""+s.charAt(1);
    return s;
  }

  public String getHour() {
    String s = time.substring(0,2);
    if (s.charAt(0)=='0') return ""+s.charAt(1);
    return s;
  }

  public String getMinutes() {
    return time.substring(3,5);
  }

  public int getHourInt() {
    return Integer.parseInt(getHour());
  }

  public int getMinutesInt() {
    return Integer.parseInt(getMinutes());
  }

  public int getDayInt() {
    return Integer.parseInt(getDay());
  }

  public String getMonth() {
    return date.substring(3,5);
  }

  public int getMonthInt() {
    return Integer.parseInt(getMonth());
  }

  public String getYear() {
    return date.substring(6,10);
  }

  public int getYearInt() {
    return Integer.parseInt(getYear());
  }

  public int getWeekDay() {
    Calendar c = Calendar.getInstance();
    c.set(getYearInt(), getMonthInt()-1, getDayInt());
    return c.get(Calendar.DAY_OF_WEEK);
  }

  public String getDayName(int day) {
    switch (day) {
      case Calendar.MONDAY:    return "Lunes";
      case Calendar.TUESDAY:   return "Martes";
      case Calendar.WEDNESDAY: return "Miércoles";
      case Calendar.THURSDAY:  return "Jueves";
      case Calendar.FRIDAY:    return "Viernes";
      case Calendar.SATURDAY:  return "Sábado";
      case Calendar.SUNDAY:    return "Domingo";
    }
    return "";
  }

  public String getMonthShortName() {
    return getMonthName(getMonth()).substring(0,3);
  }

  public String getMonthName(String month) {
    switch (month) {
      case "01": return "Enero";
      case "02": return "Febrero";
      case "03": return "Marzo";
      case "04": return "Abril";
      case "05": return "Mayo";
      case "06": return "Junio";
      case "07": return "Julio";
      case "08": return "Agosto";
      case "09": return "Septiembre";
      case "10": return "Octubre";
      case "11": return "Noviembre";
      case "12": return "Diciembre";
    }
    return "";
  }

  public boolean isToday() {
    String today = new SimpleDateFormat("dd/MM/yyy").format(new Date());
    return date.equals(today);
  }

  public boolean isTomorrow() {
    Calendar c = Calendar.getInstance();
    c.roll(Calendar.DAY_OF_MONTH, true);
    String tomorrow = "";
    String d
        = c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" +c.get(Calendar.DAY_OF_MONTH) : "" +c.get(Calendar.DAY_OF_MONTH);
    String m = c.get(Calendar.MONTH) < 10 ? "0" +(c.get(Calendar.MONTH)+1) : "" +(c.get(Calendar.MONTH)+1);
    tomorrow = d +"/" +m +"/" +c.get(Calendar.YEAR);
    return date.equals(tomorrow);
  }

  public boolean isThisMonth() {
    Calendar c = Calendar.getInstance();
    String m = c.get(Calendar.MONTH) < 10 ? "0" +(c.get(Calendar.MONTH)+1) : "" +(c.get(Calendar.MONTH)+1);
    String thisMonth = m +"/" +c.get(Calendar.YEAR);
    String eventMonth = date.substring(3);
    //System.out.println(thisMonth +" " +eventMonth);
    return eventMonth.equals(thisMonth);
  }

  public boolean isNext() {
    return !isPast();
  }

  public boolean isPast() {
    try {
      if (new SimpleDateFormat("dd/MM/yyy HH:mm:ss").parse(date +" 23:59:59").before(new Date())) return true;
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public int compareTo(@NonNull Event event) {
    if (event.getYearInt() > getYearInt()) return 1;
    else if (event.getYearInt() < getYearInt()) return -1;
    else {
      if (event.getMonthInt() > getMonthInt()) return 1;
      else if (event.getMonthInt() < getMonthInt()) return 1;
      else {
        if (event.getDayInt() > getDayInt()) return 1;
        else if (event.getDayInt() < getDayInt()) return -1;
        else {
          if (event.getHourInt() > getHourInt()) return 1;
          else if (event.getHourInt() < getHourInt()) return -1;
          else {
            if (event.getMinutesInt() > getMinutesInt()) return 1;
            else if (event.getMinutesInt() < getMinutesInt()) return -1;
            else return 0;
          }
        }
      }
    }
  }

}
