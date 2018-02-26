/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import tikape.runko.domain.Annos;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.AnnosRaakaaine;
import tikape.runko.domain.Raakaaine;

/**
 *
 * @author toukk
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        if (System.getenv("PORT") != null) {
         Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        File tiedosto = new File("db", "drinkit.db");
        Database db = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());

        AnnosDao annosDao = new AnnosDao(db);
        RaakaaineDao raakaaineDao = new RaakaaineDao(db);
        AnnosRaakaaineDao annosRaakaaineDao = new AnnosRaakaaineDao(db);

        Spark.post("/drinkki/create", (req, res) -> {
            String nimi = req.queryParams("nimi");

            System.out.println("Lisätään " + nimi);
            annosDao.saveOrUpdate(new Annos(nimi));

            res.redirect("/lisaamisnakyma");
            return "";
        });
        Spark.post("/raakaaineet/create", (req, res) -> {
            String nimi = req.queryParams("nimi");

            System.out.println("Lisätään " + nimi);
            raakaaineDao.save(new Raakaaine(nimi));

            res.redirect("/raakaaineet");
            return "";
        });
        Spark.post("/poista/drinkki/:id", (req, res) -> {
            String nimi = String.valueOf(req.params(":id"));

            System.out.println("Poistetaan " + nimi);
            annosDao.delete(nimi);

            res.redirect("/lisaamisnakyma");
            return "";
        });
        Spark.post("/poista/raakaaineet/:id", (req, res) -> {
            String nimi = String.valueOf(req.params(":id"));

            System.out.println("Poistetaan " + nimi);
            raakaaineDao.delete(nimi);

            res.redirect("/raakaaineet");
            return "";
        });
        Spark.post("/drinkkiraakaaineet/create", (req, res) -> {
            String drinkki = req.queryParams("drinkki");
            String raakaAine = req.queryParams("raakaAine");
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            System.out.println("Lisätään " + drinkki + raakaAine + maara + ohje);
            annosRaakaaineDao.save(new Annos(drinkki), new Raakaaine(raakaAine), maara, ohje);

            res.redirect("/lisaamisnakyma");
            return "";
        });

        Spark.get("/raakaaineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("lista", raakaaineDao.findAll());

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());

        Spark.get("/drinkki/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            System.out.println(String.valueOf(req.params(":id")));

            map.put("annos", annosDao.findOne(String.valueOf(req.params(":id"))));
            List<AnnosRaakaaine> ar = new ArrayList<>();

            ar = annosRaakaaineDao.findAll(String.valueOf(req.params(":id")));
            map.put("raakaaineet", ar);
            
            map.put("raakaainenimi", annosRaakaaineDao.findAllRaakaine(String.valueOf(req.params(":id"))));
            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

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
            List<Raakaaine> raakaaineet = new ArrayList<>();
            raakaaineet = raakaaineDao.findAll();
            map.put("raakaAineet", raakaaineet);
            return new ModelAndView(map, "lisaamisnakyma");
        }, new ThymeleafTemplateEngine());

    }

}
