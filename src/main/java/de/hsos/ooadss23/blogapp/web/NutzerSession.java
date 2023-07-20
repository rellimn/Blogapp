package de.hsos.ooadss23.blogapp.web;

import de.hsos.ooadss23.blogapp.datenmodell.Nutzer;

/**
 * Nutzer-Wrapper zur Verwendung als Session-Attribut
 * @author Roman Wasenmiller
 */
public class NutzerSession {
    private Nutzer nutzer;

    public NutzerSession() {

    }

    public NutzerSession(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getName() {
        return this.nutzer.getName();
    }

    public int getId() {
        return this.nutzer.getId();
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }
    public Nutzer getNutzer() {
       return this.nutzer;
    }

}
