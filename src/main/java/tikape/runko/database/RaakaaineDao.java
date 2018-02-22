/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import tikape.runko.domain.Raakaaine;
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
public class RaakaaineDao {

    private Database database;

    public RaakaaineDao(Database database) {
        this.database = database;
    }
    
    public Raakaaine findOne(String key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
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

    public List<Raakaaine> findAll() throws SQLException {
        List<Raakaaine> Raakaaineet = new ArrayList<>();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Raakaaine");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Raakaaine a = new Raakaaine(rs.getString("nimi"));
            Raakaaineet.add(a);
        }

        stmt.close();
        rs.close();

        conn.close();

        return Raakaaineet;
    }

    public Raakaaine saveOrUpdate(Raakaaine object) throws SQLException {
        Raakaaine tarkastelu = findOne(object.getNimi());
        if (tarkastelu == null) {
            return save(tarkastelu);
        } else {

            return update(tarkastelu);
        }
    }

    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Raakaaine WHERE nimi = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private Raakaaine save(Raakaaine raakaaine) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Raakaaine"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, raakaaine.getNimi());

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
