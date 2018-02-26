/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author antlammi
 */
public class AnnosRaakaaine {
    private String annos_nimi;
    private String raakaaine_nimi;
    private String maara;
    private String ohje;
    public AnnosRaakaaine(String annosnimi, String raakaainenimi, String maara, String ohje){
        this.annos_nimi = annosnimi;
        this.raakaaine_nimi = raakaainenimi;
        this.maara = maara;
        this.ohje = ohje;
    }
    public String getAnnosNimi(){
        return this.annos_nimi;
    } 
    public String getRaakaaineNimi(){
        return this.raakaaine_nimi;
    }
    public String getMaara(){
        return this.maara;
    }
    public String getOhje(){
        return this.ohje;
    }
}
