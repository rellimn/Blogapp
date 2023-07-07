package de.hsos.ooadss23.blogapp.entitaet;

import java.time.LocalDateTime;
import java.util.List;

public class Blog {
    private int id;
    private LocalDateTime zeitstempel;
    private String ueberschrift;
    private List<Artikel> artikel;
    private Nutzer ersteller;
    public Blog(int id, LocalDateTime zeitstempel, String ueberschrift, List<Artikel> artikel, Nutzer ersteller) {
        this.id = id;
        this.zeitstempel = zeitstempel;
        this.ueberschrift = ueberschrift;
        this.artikel = artikel;
        this.ersteller = ersteller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(LocalDateTime zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public List<Artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(List<Artikel> artikel) {
        this.artikel = artikel;
    }

    public Nutzer getErsteller() {
        return ersteller;
    }

    public void setErsteller(Nutzer ersteller) {
        this.ersteller = ersteller;
    }
}
