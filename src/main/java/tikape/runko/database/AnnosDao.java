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
public class AnnosDao implements Dao< Annos, Integer> {

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

    @Override
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

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        Annos tarkastelu = findOne(object.getNimi());
        if (tarkastelu == null) {
            return save(object);
        } else {

            return update(object);
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE nimi = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private Annos save(Annos annos) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, annos.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Annos"
                + " WHERE nimi = ?");
        stmt.setString(1, annos.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos

        Annos a = new Annos(rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return a;
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

    @Override
    public Annos findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
