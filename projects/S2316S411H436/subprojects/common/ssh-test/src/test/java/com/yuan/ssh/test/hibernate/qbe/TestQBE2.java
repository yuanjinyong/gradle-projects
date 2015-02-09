package com.yuan.ssh.test.hibernate.qbe;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
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
 * 测试QBE查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional
public class TestQBE2 {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /** QBE查询 适合条件检索 */
    @Test
    public void test_qbe() {
        Emp emp = new Emp();
        emp.setDeptno(10);
        emp.setJob("MANAGER");

        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.add(Example.create(emp));// 注意
        List<Emp> list = criteria.list();
        show(list);
        session.close();
    }

    // 遍历集合
    private void show(List<Emp> list) {
        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

}
