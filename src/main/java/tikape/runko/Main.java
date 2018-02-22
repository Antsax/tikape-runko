/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import tikape.runko.domain.Annos;
import java.io.File;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;

/**
 *
 * @author toukk
 */
public class Main {

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) throws Exception {
        

        File tiedosto = new File("db", "drinkit.db");
        Database db = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());

        AnnosDao annosDao = new AnnosDao(db);
        
        
        
        Spark.post("/lisaamisnakyma", (req, res) -> {
            String nimi = req.queryParams("nimi");

            System.out.println("Lisätään " + nimi);
            annosDao.saveOrUpdate(new Annos(nimi));

            res.redirect("/lisaamisnakyma");
            return "";
        });

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("lista", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
        Spark.get("/lisaamisnakyma", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Annos> annokset = new ArrayList<>();
            annokset = annosDao.findAll();
            map.put("annokset", annokset);
            
            return new ModelAndView(map, "lisaamisnakyma");
        }, new ThymeleafTemplateEngine());
        
    }
       
       
}
