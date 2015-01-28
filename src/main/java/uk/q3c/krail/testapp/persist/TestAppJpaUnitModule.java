package uk.q3c.krail.testapp.persist;

import uk.q3c.krail.persist.jpa.JpaConfig;
import uk.q3c.krail.persist.jpa.JpaUnitModule;

/**
 * Created by David Sowerby on 03/01/15.
 */
public class TestAppJpaUnitModule extends JpaUnitModule {
    /**
     * Configures the persistence units over the exposed methods.
     */
    @Override
    protected void configurePersistence() {
        addPersistenceUnit("derbyDb", Jpa1.class, derbyConfig());
        addPersistenceUnit("hsqlDb", Jpa2.class, hsqlConfig());

        //        this.bindApplicationManagedPersistenceUnit("derbyDb")
        //            .annotatedWith(Jpa1.class);
        //        this.bindApplicationManagedPersistenceUnit("hsqlDb")
        //            .annotatedWith(Jpa2.class);

        //        this.bindContainerManagedPersistenceUnit(derbyEntityManagerFactory)
        //            .annotatedWith(Jpa1.class);
        //        this.bindContainerManagedPersistenceUnit(hsqlEntityManagerFactory)
        //            .annotatedWith(Jpa2.class);
    }

    private JpaConfig derbyConfig() {
        JpaConfig config = new JpaConfig();

        config.transactionType(JpaConfig.TransactionType.RESOURCE_LOCAL)
              .db(JpaConfig.Db.DERBY_EMBEDDED)
              .url("/home/david/temp/derbyDb", true)
              .user("test")
              .password("test")
              .ddlGeneration(JpaConfig.Ddl.DROP_AND_CREATE);
        return config;
    }

    private JpaConfig hsqlConfig() {
        JpaConfig config = new JpaConfig();
        config.db(JpaConfig.Db.HSQLDB)
              .url("mem:test", true)
              .user("sa")
              .password("")
              .ddlGeneration(JpaConfig.Ddl.DROP_AND_CREATE);
        return config;
    }

}