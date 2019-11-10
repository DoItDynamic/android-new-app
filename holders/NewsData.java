package com.smartstudio.sajmovi.eu.holders;

/**
 * Created by Boris on 5.6.2015..
 */
public class NewsData {
    private String naslov,nadnaslov, thumbnailUrl,datum,rubrika,gallery_items;
    private String kalendar_naziv, kalendar_opis,event_start,event_end;
    private Integer id;
    public NewsData() {
    }

    public NewsData(String name, String name2, String thumbnailUrl, String datum, String rubrika, Integer ID, String galleryItems, String kalendar_naziv, String kalendar_opis, String event_start, String event_end) {

        //kalendar items
        this.kalendar_naziv=kalendar_naziv;
        this.kalendar_opis=kalendar_opis;
        this.event_start=event_start;
        this.event_end=event_end;

        this.rubrika = rubrika;
        this.nadnaslov = name2;
        this.naslov = name;
        this.thumbnailUrl = thumbnailUrl;
        this.datum = datum;
        this.id=ID;
        this.gallery_items=galleryItems;
    }
    //naslov
    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String name) {
        this.naslov = name;
    }

    //ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    //galleryItems
    public String getGallery_items() {
        return gallery_items;
    }

    public void setGallery_items(String gallery_items) {
        this.gallery_items = gallery_items;
    }

    //nadnaslov
    public String getNadnaslov() {
        return nadnaslov;
    }

    public void setNadnaslov(String name2) {
        this.nadnaslov= name2;
    }
    //slika
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    //datum
    public String getDatum() {
        return datum;
    }

    public void setDatum(String year) {
        this.datum = year;
    }
    //rubrika
    public String getRubrika() {
        return rubrika;
    }

    public void setRubrika(String rubrika) {
        this.rubrika = rubrika;
    }

    //kalendar items
    //kalendar_naziv
    public String getKalendar_naziv() {
        return kalendar_naziv;
    }
    public void setKalendar_naziv(String kalendar_naziv) {
        this.kalendar_naziv = kalendar_naziv;
    }
    //kalendar_opis
    public String getKalendar_opis() {
        return kalendar_opis;
    }
    public void setKalendar_opis(String kalendar_opis) {
        this.kalendar_opis = kalendar_opis;
    }
    //event_start
    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }
    //event_end
    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }


}
