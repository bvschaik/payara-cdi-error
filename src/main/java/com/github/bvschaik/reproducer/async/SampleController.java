package com.github.bvschaik.reproducer.async;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
@ApplicationScoped
public class SampleController {

    @Inject
    private AsyncWrapper asyncProcess;

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("create")
    public String createEntityAsync() {
        asyncProcess.doStuff();
        return "async started";
    }

    @GET
    @Path("count")
    public String countEntities() {
        return String.valueOf(em.createQuery("select count(e.id) from SampleEntity e").getSingleResult());
    }
}
