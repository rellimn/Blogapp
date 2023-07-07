package de.hsos.ooadss23.blogapp.entitaet;

import java.util.List;

public class Artikel {
    private int id;
    private String ueberschrift;
    private Text text;
    private List<Kommentar> kommentare;
    public Artikel(int id, String ueberschrift, Text text, List<Kommentar> kommentare) {
        this.id = id;
        this.ueberschrift = ueberschrift;
        this.text = text;
        this.kommentare = kommentare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public List<Kommentar> getKommentare() {
        return kommentare;
    }

    public void setKommentare(List<Kommentar> kommentare) {
        this.kommentare = kommentare;
    }
}
