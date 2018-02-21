/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drinkkiappi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author toukk
 */
public class Drinkkiappi {

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) throws Exception {
        

        File tiedosto = new File("db", "drinkit.db");
        Database db = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());

        AnnosDao annosDao = new AnnosDao(db);
        
        
        
        Spark.post("/tehtavat", (req, res) -> {
            String nimi = req.queryParams("nimi");

            System.out.println("Lisätään " + nimi);
            annosDao.saveOrUpdate(new Annos( nimi));

            res.redirect("/tehtavat");
            return "";
        });

        Spark.get("*", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Annos> annokset = new ArrayList<>();
            annokset = annosDao.findAll();
            map.put("tehtavat", annokset);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

    }
}
