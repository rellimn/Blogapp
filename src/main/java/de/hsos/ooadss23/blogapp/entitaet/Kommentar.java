package de.hsos.ooadss23.blogapp.entitaet;

public class Kommentar {
    private int id;
    private Text text;

    public Kommentar(int id, Text text) {
        this.id = id;
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

}
