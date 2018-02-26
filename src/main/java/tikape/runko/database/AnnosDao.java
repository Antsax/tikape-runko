/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import tikape.runko.domain.Annos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.*;

/**
 *
 * @author toukk
 */
public class AnnosDao {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    public Annos findOne(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE nimi = ?");
        stmt.setString(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Annos a = new Annos(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

   
    public List<Annos> findAll() throws SQLException {
        List<Annos> Annokset = new ArrayList<>();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Annos a = new Annos(rs.getString("nimi"));
            Annokset.add(a);
        }

        stmt.close();
        rs.close();

        conn.close();

        return Annokset;
    }

   
    public Annos saveOrUpdate(Annos object) throws SQLException {
        Annos tarkastelu = findOne(object.getNimi());
        if (tarkastelu == null) {
            return save(object);
        } else {

            return update(object);
        }
    }

    public void delete(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE nimi = ?");
        
        stmt.setString(1, key);
        stmt.executeUpdate();
        
        
        stmt.close();
        
        stmt = conn.prepareStatement("DELETE FROM AnnosRaakaaine where annos_nimi = ?");
        stmt.setString(1, key);
        stmt.executeUpdate();
        conn.close();
    }

    private Annos save(Annos annos) throws SQLException {

        Connection conn = database.getConnection();
        if (!annos.getNimi().isEmpty()){
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, annos.getNimi());

        stmt.executeUpdate();
        stmt.close();

        PreparedStatement stmt1
                = conn.prepareStatement("SELECT * FROM Annos"
                        + " WHERE nimi = ?");
        stmt1.setString(1, annos.getNimi());

        ResultSet rs = stmt1.executeQuery();
        rs.next();
        Annos a = new Annos(rs.getString("nimi"));

        stmt1.close();
        rs.close();

        conn.close();

        return a;
        } else {
            conn.close();
            return null;
        }
    }

    // Huom, en implementoi tata kun ei tarvita
    private Annos update(Annos annos) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Annos SET"
                + " nimi = * WHERE id = ?");
        stmt.setString(1, annos.getNimi());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return annos;
    }

    
    
}
