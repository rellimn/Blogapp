package de.hsos.ooadss23.blogapp.entitaet;

import java.time.LocalDateTime;

public class Text {
    private int id;
    private String inhalt;
    private LocalDateTime zeitstempel;
    private Nutzer verfasser;

    public Text(int id, String inhalt, LocalDateTime zeitstempel, Nutzer verfasser) {
        this.id = id;
        this.inhalt = inhalt;
        this.zeitstempel = zeitstempel;
        this.verfasser = verfasser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(LocalDateTime zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public Nutzer getVerfasser() {
        return verfasser;
    }

    public void setVerfasser(Nutzer verfasser) {
        this.verfasser = verfasser;
    }
}
