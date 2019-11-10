package com.smartstudio.sajmovi.eu.holders;

/**
 * Created by Boris on 5.6.2015..
 */
public class CalendarData {
    // ----------------------  LIST ROW DATA  ----------------------------
    private Integer id;
    private String kalendar_naziv;
    private String kalendar_opis;
    private String event_start;
    private String event_end;
    private String kalendar_mjesec;
    private String kalendar_status;
    private String kalendar_city;
    private String kalendar_country;
    private String kalendar_flag;
    private int brojac;
    public CalendarData(){}

    // ----------------------  FULL CALENDAR DATA  ----------------------------


    public CalendarData(String kalendar_naziv,String kalendar_opis,String event_start,String event_end, String kalendar_mjesec,Integer id,String kalendar_status,String kalendar_city,String kalendar_country,String kalendar_flag,int brojac) {
        // ----------------------  LIST ROW DATA  ----------------------------
        this.id=id;
        this.kalendar_naziv = kalendar_naziv;
        this.kalendar_opis = kalendar_opis;
        this.event_start = event_start;
        this.event_end = event_end;
        this.kalendar_mjesec = kalendar_mjesec;
        this.kalendar_status=kalendar_status;
        this.kalendar_city=kalendar_city;
        this.kalendar_country=kalendar_country;
        this.kalendar_flag=kalendar_flag;
        this.brojac=brojac;

        // ----------------------  FULL CALENDAR DATA  ----------------------------

    }
    // ----------------------  LIST ROW DATA  ----------------------------
    public String getKalendar_naziv() {
        return kalendar_naziv;
    }

    public void setKalendar_naziv(String kalendar_naziv) {
        this.kalendar_naziv = kalendar_naziv;
    }

    public String getKalendar_opis() {
        return kalendar_opis;
    }

    public void setKalendar_opis(String kalendar_opis) {
        this.kalendar_opis = kalendar_opis;
    }

    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }

    public String getKalendar_mjesec() {
        return kalendar_mjesec;
    }

    public void setKalendar_mjesec(String kalendar_mjesec) {
        this.kalendar_mjesec = kalendar_mjesec;
    }
    //ID
    public Integer getIdCalendar() {
        return id;
    }

    public void setIdCalendar(Integer ID) {
        this.id = ID;
    }

    public String getKalendar_status() {
        return kalendar_status;
    }

    public void setKalendar_status(String kalendar_status) {
        this.kalendar_status = kalendar_status;
    }
    public String getKalendar_city() {
        return kalendar_city;
    }

    public void setKalendar_city(String kalendar_city) {
        this.kalendar_city = kalendar_city;
    }
    public String getKalendar_country() {
        return kalendar_country;
    }

    public void setKalendar_country(String kalendar_country) {
        this.kalendar_country = kalendar_country;
    }
    public String getKalendar_flag() {
        return kalendar_flag;
    }

    public void setKalendar_flag(String kalendar_flag) {
        this.kalendar_flag = kalendar_flag;
    }

    //brojac
    public int getBrojac() {
        return brojac;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    // ----------------------  FULL CALENDAR DATA  ----------------------------


}
