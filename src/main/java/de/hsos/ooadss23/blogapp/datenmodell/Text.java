package de.hsos.ooadss23.blogapp.datenmodell;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * JPA-Entit&auml;t, die einen Nutzer repr&auml;sentiert.
 * Entspricht der Datenbanktabelle "Text".
 * @author Roman Wasenmiller
 */
@Entity
public class Text {
    @Id
    @GeneratedValue
    private int id;
    @Lob
    @Column
    // Inhalt wird als CLOB in der Datenbank gespeichert, aber als Java-String interpretiert
    private String inhalt;
    @CreationTimestamp
    private LocalDateTime zeitstempel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Nutzer verfasser;

    protected Text() {

    }

    public Text(String inhalt, Nutzer verfasser) {
        this.inhalt = inhalt;
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
