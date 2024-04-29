/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Competitor;
import com.example.models.CompetitorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Sebas
 */
@Path("/competitors")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

    //Contextio que vamos a usar, unidad persistente indicada anteriormente
    @PersistenceContext(unitName = "CompetitorsPU")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Metodo enfocado a obtener competidores de ejemplo para la base de datos
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTest() {

        List<Competitor> competitors = new ArrayList<Competitor>();

        Competitor competitorTmp = new Competitor("Carlos", "Alvarez", 35, "7658463", "3206574839 ", "carlos.alvarez@gmail.com", "Bogota", "Colombia", false);
        Competitor competitorTmp2 = new Competitor("Gustavo", "Ruiz", 55, "2435231", "3101325467", "gustavo.ruiz@gmail.com", "Buenos Aires", "Argentina", false);
        competitors.add(competitorTmp);
        competitors.add(competitorTmp2);
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
    }

    /**
     *
     * Metodo de prueba para crear un nuevo competidor
     */

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitorTest(CompetitorDTO competitor) {

        Competitor competitorTmp = new Competitor(competitor.getName(), competitor.getSurname(), competitor.getAge(), competitor.getTelephone(), competitor.getCellphone(), competitor.getAddress(), competitor.getCity(), competitor.getCountry(), false);
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitorTmp).build();
    }

    /**
     * 
     * @return 
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Competitor u order by u.surname ASC");
        List<Competitor> competitors = q.getResultList();

//        System.out.println("competidores " + competitors.get(0));
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
    }

    @POST
    @Path("/addCompetitor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitor(CompetitorDTO competitor) {
        JSONObject rta = new JSONObject();
        Competitor competitorTmp = new Competitor();
        competitorTmp.setAddress(competitor.getAddress());
        competitorTmp.setAge(competitor.getAge());
        competitorTmp.setCellphone(competitor.getCellphone());
        competitorTmp.setCity(competitor.getCity());
        competitorTmp.setCountry(competitor.getCountry());
        competitorTmp.setName(competitor.getName());
        competitorTmp.setSurname(competitor.getSurname());
        competitorTmp.setTelephone(competitor.getTelephone());

        System.out.println("Adicionando");
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(competitorTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(competitorTmp);
            rta.put("competitor_id", competitorTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            competitorTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCompetitor(@PathParam("id") Long id, CompetitorDTO competitor) throws JSONException {
        JSONObject rta = new JSONObject();
        try {
            Competitor competitorTmp = entityManager.find(Competitor.class, id);
            if (competitorTmp != null) {
                entityManager.getTransaction().begin();
                competitorTmp.setAddress(competitor.getAddress());
                competitorTmp.setAge(competitor.getAge());
                competitorTmp.setCellphone(competitor.getCellphone());
                competitorTmp.setCity(competitor.getCity());
                competitorTmp.setCountry(competitor.getCountry());
                competitorTmp.setName(competitor.getName());
                competitorTmp.setSurname(competitor.getSurname());
                competitorTmp.setTelephone(competitor.getTelephone());
                entityManager.merge(competitorTmp);
                entityManager.getTransaction().commit();
                rta.put("message", "Competitor updated successfully");
            } else {
                rta.put("message", "Competitor not found");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            rta.put("message", "Error updating competitor");
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompetitor(@PathParam("id") Long id) throws JSONException {
        JSONObject rta = new JSONObject();
        try {
            Competitor competitorTmp = entityManager.find(Competitor.class, id);
            if (competitorTmp != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(competitorTmp);
                entityManager.getTransaction().commit();
                rta.put("message", "Competitor deleted successfully");
            } else {
                rta.put("message", "Competitor not found");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            rta.put("message", "Error deleting competitor");
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }

}
