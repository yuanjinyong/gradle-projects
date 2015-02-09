/**
 *
 */
package com.yuan.ssh.hibernate.utils;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * @author Yuanjy
 *
 */
public final class SessionFactoryUtils {
    private static SessionFactory sessionFactory;

    private SessionFactoryUtils() {

    }

    static {
        try {
            //Hibernate4取得SessionFactory的方法
            Configuration config = new Configuration().configure();
            sessionFactory = config.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(
                    config.getProperties()).build());
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
