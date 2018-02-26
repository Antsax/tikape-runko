/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaaine;
import tikape.runko.domain.Raakaaine;

/**
 *
 * @author antlammi
 */
public class AnnosRaakaaineDao {

    private Database database;

    public AnnosRaakaaineDao(Database database) {
        this.database = database;
    }

    public Raakaaine findOne(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos, Raakaine WHERE nimi = ?");
        stmt.setString(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Raakaaine a = new Raakaaine(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

    public List<AnnosRaakaaine> findAll(String nimi) throws SQLException {
        List<AnnosRaakaaine> annosRaakaaineet = new ArrayList<>();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT raakaaine_nimi, maara, ohje FROM AnnosRaakaaine WHERE annos_nimi = ?");
        stmt.setString(1, nimi);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
           String a = rs.getString("raakaaine_nimi");
           String b = rs.getString("maara");
           String c = rs.getString("ohje");
            AnnosRaakaaine ar = new AnnosRaakaaine(nimi, a, b, c);
            
            annosRaakaaineet.add(ar);
        }

        stmt.close();
        rs.close();

        conn.close();

        return annosRaakaaineet;
    }
    
    
    
    
    

    public List<Raakaaine> findAllRaakaine(String annosnimi) throws SQLException {
        List<Raakaaine> Raakaaineet = new ArrayList<>();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT raakaaine_nimi FROM AnnosRaakaaine WHERE annos_nimi = ?");
        stmt.setString(1, annosnimi);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Raakaaine a = new Raakaaine(rs.getString("raakaaine_nimi"));
            Raakaaineet.add(a);
        }

        stmt.close();
        rs.close();

        conn.close();

        return Raakaaineet;
    }

    public Raakaaine save(Annos annos, Raakaaine raakaaine, String maara, String ohje) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaaine"
                + " (annos_nimi, raakaaine_nimi, maara, ohje)"
                + " VALUES (?, ?, ?, ?)");
        stmt.setString(1, annos.getNimi());
        stmt.setString(2, raakaaine.getNimi());
        stmt.setString(3, maara);
        stmt.setString(4, ohje);

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Raakaaine"
                + " WHERE nimi = ?");
        stmt.setString(1, raakaaine.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos

        Raakaaine a = new Raakaaine(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

    // Huom, en implementoi tata kun ei tarvita
    private Raakaaine update(Raakaaine raakaaine) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Raakaaine SET"
                + " nimi = * WHERE id = ?");
        stmt.setString(1, raakaaine.getNimi());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return raakaaine;
    }

  
   
}
