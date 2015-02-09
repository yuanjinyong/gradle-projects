package com.yuan.ssh.test.hibernate.cache;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.yuan.ssh.hibernate.utils.SessionFactoryUtils;
import com.yuan.ssh.test.domain.Emp;


/**
 * 测试Hibernate缓存
 */
public class TestHibernateCache {

    // 测试一级缓存：Session缓存
    @Test
    public void test_cache1() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        Emp emp1 = (Emp) session.get(Emp.class, 7369);
        System.out.println(emp1);
        Emp emp2 = (Emp) session.get(Emp.class, 7369);// 如果启用缓存，第二次查询时将直接从缓存中获取，因此不会打印SQL
        System.out.println(emp2);

        System.out.println("emp1==emp2：" + (emp1 == emp2));
    }

    // 测试二级缓存：EHCache缓存
    // 配置缓存的话，会执行一条SQL，否则是2条SQL
    @Test
    public void test_cache2() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp";

        Query query = session.createQuery(hql);
        query.setCacheable(true);//启动缓存
        List<Emp> list1 = query.list();
        show(list1);

        query = session.createQuery(hql);
        query.setCacheable(true);//启动缓存
        List<Emp> list2 = query.list();// 如果启用缓存，第二次查询时将直接从缓存中获取，因此不会打印SQL
        show(list2);

        System.out.println("list1==list2：" + (list1 == list2));
    }

    // 遍历集合
    private void show(List<Emp> list) {
        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

}
