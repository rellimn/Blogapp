package de.hsos.ooadss23.blogapp.datenmodell;

import jakarta.persistence.*;

/**
 * JPA-Entit&auml;t, die eine Artikelbewertung repr&auml;sentiert.
 * Entspricht der Datenbanktabelle "Bewertung".
 * @author Ludwig Cornelius
 */
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

    public void setSterne(int sterne) {
        this.sterne = sterne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nutzer getVerfasser() {
        return verfasser;
    }

    public void setVerfasser(Nutzer verfasser) {
        this.verfasser = verfasser;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }
}
