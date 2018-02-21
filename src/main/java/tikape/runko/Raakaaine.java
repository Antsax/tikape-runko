/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drinkkiappi;

/**
 *
 * @author toukk
 */
public class Raakaaine {
    

    private String nimi;
    private Integer id;

    public Raakaaine(Integer id, String nimi) {
        this.nimi = nimi;
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
