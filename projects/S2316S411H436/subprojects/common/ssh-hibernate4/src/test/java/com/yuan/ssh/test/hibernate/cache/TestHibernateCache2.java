package com.yuan.ssh.test.hibernate.cache;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yuan.ssh.test.domain.Emp;


/**
 * 测试SQL连接：交叉连接、内连接、左连接、右连接等
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional
public class TestHibernateCache2 {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    // 测试一级缓存：Session缓存
    @Test
    public void test_cache1() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
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
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
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
