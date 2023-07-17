package de.hsos.ooadss23.blogapp.datenbank;

import jakarta.persistence.*;

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"verfasser_id", "artikel_id"})
)
@Entity
public class Bewertung {
    @Id
    @GeneratedValue
    private int id;


    @Column
    private int sterne;

    @ManyToOne(fetch = FetchType.LAZY)
    private Nutzer verfasser;
    @ManyToOne(fetch = FetchType.LAZY)
    private Artikel artikel;

    protected Bewertung() {

    }

    public Bewertung(int sterne, Nutzer verfasser, Artikel artikel) {
        this.sterne = sterne;
        this.verfasser = verfasser;
        this.artikel = artikel;
    }

    public int getSterne() {
        return sterne;
    }



}
