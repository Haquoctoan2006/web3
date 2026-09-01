package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConfig {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("jpa-hibernate-mysql");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
