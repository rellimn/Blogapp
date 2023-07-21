package de.hsos.ooadss23.blogapp.datenmodell;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA-Entit&auml;t, die einen Blog repr&auml;sentiert.
 * Entspricht der Datenbanktabelle "Blog".
 * @author Roman Wasenmiller
 */
@Entity
public class Blog {
    @Id
    @GeneratedValue
    private int id;
    @CreationTimestamp
    private LocalDateTime zeitstempel;
    private String ueberschrift;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "blog")
    private List<Artikel> artikel;
    @ManyToOne
    private Nutzer ersteller;

    protected Blog() {

    }

    public Blog(String ueberschrift, Nutzer ersteller) {
        this.ueberschrift = ueberschrift;
        this.artikel = new ArrayList<>();
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

    public void addArtikel(Artikel artikel) {
        this.artikel.add(artikel);
        artikel.setBlog(this);
    }

    public void removeArtikel(Artikel artikel) {
        this.artikel.remove(artikel);
        artikel.setBlog(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blog blog)) return false;
        return id == blog.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", zeitstempel=" + zeitstempel +
                ", ueberschrift='" + ueberschrift + '\'' +
                ", artikel=" + artikel +
                ", ersteller=" + ersteller +
                '}';
    }
}
