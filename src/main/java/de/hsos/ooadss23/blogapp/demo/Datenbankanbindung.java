package de.hsos.ooadss23.blogapp.demo;


import de.hsos.ooadss23.blogapp.entitaet.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Datenbankanbindung implements AutoCloseable {
    private final static String url = "jdbc:derby:Datenbank";
    private final Connection con;

    Datenbankanbindung() throws SQLException{
        this.con = DriverManager.getConnection(Datenbankanbindung.url);
    }

    public List<Blog> alleBlogs() throws SQLException {
        final String sql = """
                SELECT BLOG.*, N.ID AS "ERSTELLER.ID", N.NAME FROM BLOG
                LEFT JOIN NUTZER N ON N.ID = BLOG.ERSTELLER_ID""";

        List<Blog> blogs = new ArrayList<>();

        Statement statement = this.con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next()) {
            int id = result.getInt("ID");
            LocalDateTime zeitstempel = result.getTimestamp("ZEITSTEMPEL").toLocalDateTime();
            String ueberschrift = result.getString("UEBERSCHRIFT");
            List<Artikel> artikel = this.alleArtikelInBlog(id);
            Nutzer ersteller = new Nutzer(result.getInt("ERSTELLER.ID"), result.getString("NAME"));
            Blog blog = new Blog(id, zeitstempel, ueberschrift, artikel, ersteller);
            blogs.add(blog);
        }
        result.close();
        statement.close();
        return blogs;
    }

    public List<Artikel> alleArtikelInBlog(int blogId) throws SQLException {
        final String sql = """
                SELECT ARTIKEL.*, T.INHALT, T.VERFASSER_ID, T.ZEITSTEMPEL, N.NAME
                FROM ARTIKEL, TEXT as T, NUTZER as N
                WHERE T.ID = ARTIKEL.TEXT_ID
                  AND N.ID = T.VERFASSER_ID
                  AND BLOG_ID=?""";

        List<Artikel> artikelListe = new ArrayList<>();

        PreparedStatement statement = this.con.prepareStatement(sql);
        statement.setInt(1, blogId);
        ResultSet result = statement.executeQuery();
        while(result.next()) {
            int artikelId = result.getInt("ID");
            String ueberschrift = result.getString("UEBERSCHRIFT");
            Text text = Datenbankanbindung.textAusResult(result);
            List<Kommentar> kommentare = this.alleKommentareInArtikel(artikelId);
            Artikel artikel = new Artikel(artikelId, ueberschrift, text, kommentare);
            artikelListe.add(artikel);
        }
        result.close();
        statement.close();
        return artikelListe;
    }

    public List<Kommentar> alleKommentareInArtikel(int artikelId) throws SQLException {
        final String sql = """
                SELECT KOMMENTAR.ID, KOMMENTAR.TEXT_ID, T.INHALT,T.VERFASSER_ID, T.ZEITSTEMPEL, N.NAME
                FROM KOMMENTAR, TEXT as T, NUTZER as N
                WHERE T.ID = KOMMENTAR.TEXT_ID
                  AND N.ID = T.VERFASSER_ID
                  AND ARTIKEL_ID=?""";

        List<Kommentar> kommentare = new ArrayList<>();

        PreparedStatement statement = this.con.prepareStatement(sql);
        statement.setInt(1, artikelId);
        ResultSet result = statement.executeQuery();
        while(result.next()) {
            int kommentarId = result.getInt("ID");
            Text text = Datenbankanbindung.textAusResult(result);
            Kommentar kommentar = new Kommentar(kommentarId, text);
            kommentare.add(kommentar);
        }
        result.close();
        statement.close();
        return kommentare;
    }

    public Artikel getArtikel(int artikelId) throws SQLException {
        final String sql = """
                SELECT ARTIKEL.*, T.INHALT,T.VERFASSER_ID, T.ZEITSTEMPEL, N.NAME
                  FROM ARTIKEL, TEXT as T, NUTZER as N
                 WHERE ARTIKEL.ID = ?
                   AND ARTIKEL.TEXT_ID = T.ID
                   AND T.VERFASSER_ID = N.ID""";

        PreparedStatement statement = this.con.prepareStatement(sql);
        statement.setInt(1, artikelId);
        ResultSet result = statement.executeQuery();
        result.next();

        String ueberschrift = result.getString("UEBERSCHRIFT");
        Text text = Datenbankanbindung.textAusResult(result);
        List<Kommentar> kommentare = this.alleKommentareInArtikel(artikelId);

        result.close();
        statement.close();
        return new Artikel(artikelId, ueberschrift, text, kommentare);
    }

    private static Text textAusResult(ResultSet result) throws SQLException{
        int textId = result.getInt("TEXT_ID");
        String inhalt = result.getString("INHALT");
        LocalDateTime zeitstempel = result.getTimestamp("ZEITSTEMPEL").toLocalDateTime();
        Nutzer verfasser = new Nutzer(result.getInt("VERFASSER_ID"), result.getString("NAME"));
        return new Text(textId, inhalt, zeitstempel, verfasser);
    }

//    public Artikel einArtikel() {
//        return new Artikel();
//    }

    @Override
    public void close() throws SQLException {
        this.con.close();
    }
}
