package de.hsos.ooadss23.blogapp.datenmodell;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * JPA-Entit&auml;t, die eine Artikelbewertung repr&auml;sentiert.
 * Entspricht der Datenbanktabelle "Kommentar".
 * @author Roman Wasenmiller
 */
@Entity
public class Kommentar {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Text text;
    @ManyToOne(fetch = FetchType.LAZY)
    private Artikel artikel;

    protected Kommentar() {

    }

    public Kommentar(Text text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kommentar kommentar)) return false;
        return id == kommentar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
