package com.yuan.ssh.test.hibernate.qbc;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
 * 测试QBC查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration
@Transactional
public class TestQBC2 {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /** 查询全部 */
    @Test
    public void test_list() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.setCacheable(true); // 设置缓存
        List<Emp> list = criteria.list();
        show(list);
        session.close();
    }

    /** 查询全部 */
    @Test
    public void test_iterator() {
        // 没有iterator
    }

    /** 分页查询 */
    // select * from
    // ( select e.*, rownum rn from ( select * from emp ) e where rownum <= 10 )
    // where rn > 1
    @Test
    public void test_pagination() {
        int pageNo = 1; // 当前页数
        int pageSize = 5; // 每页显示条数

        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.setFirstResult((pageNo - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        criteria.setCacheable(true); // 设置缓存
        List<Emp> list = criteria.list();
        show(list);
        session.close();
    }

    /** 查询结果是唯一值 */
    @Test
    public void test_uniqueResult() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.add(Restrictions.eq("empno", 7369));
        Emp emp = (Emp) criteria.uniqueResult();
        System.out.println(emp);
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection1() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.setProjection(Projections.property("empno"));
        List<Integer> list = criteria.list();
        for (Integer empno : list) {
            System.out.println(empno);
        }
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection2() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("empno"));
        projectionList.add(Projections.property("ename"));
        projectionList.add(Projections.property("hiredate"));
        projectionList.add(Projections.property("sal"));
        criteria.setProjection(projectionList);
        List<Object[]> list = criteria.list();
        for (Object[] arr : list) {
            System.out.println(arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3]);
        }
        session.close();
    }

    /** 去掉重复值 */
    @Test
    public void test_distinct() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.setProjection(Projections.distinct(Projections.property("job")));
        List<String> list = criteria.list();
        for (String job : list) {
            System.out.println(job);
        }
        session.close();
    }

    /** 条件查询 */
    @Test
    public void test_where() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);

        // criteria.add(Restrictions.and(Restrictions.eq("empno", 7369),
        // Restrictions.eq("ename", "SMITH")));
        // criteria.add(Restrictions.or(Restrictions.eq("empno", 7369),
        // Restrictions.eq("ename", "ALLEN")));
        // criteria.add(Restrictions.not(Restrictions.eq("ename", "SMITH")));

        // criteria.add(Restrictions.between("sal", 3000.0, 5000.0));
        // criteria.add(Restrictions.not(Restrictions.between("sal", 3000.0,
        // 5000.0)));

        // criteria.add(Restrictions.in("empno", new Object[] { 7369, 7900, 7654
        // }));
        // criteria.add(Restrictions.not(Restrictions.in("empno", new Object[] {
        // 7369, 7900, 7654 })));

        // criteria.add(Restrictions.like("ename", "S%", MatchMode.START));
        // criteria.add(Restrictions.not(Restrictions.like("ename", "S%",
        // MatchMode.START)));

        // criteria.add(Restrictions.isNull("comm"));
        // criteria.add(Restrictions.not(Restrictions.isNull("comm")));

        // criteria.add(Restrictions.and(Restrictions.ge("sal", 3000.0),
        // Restrictions.le("sal", 5000.0)));
        criteria.add(Restrictions.ne("sal", 3000.0));
        List<Emp> list = criteria.list();
        show(list);
        session.close();
    }

    /** 统计函数 */
    @Test
    public void test_statistics1() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.min("sal"));
        projectionList.add(Projections.max("sal"));
        projectionList.add(Projections.avg("sal"));
        projectionList.add(Projections.sum("sal"));
        criteria.setProjection(projectionList);
        Object[] values = (Object[]) criteria.uniqueResult();
        System.out.println("总数：" + values[0]);
        System.out.println("最小值：" + values[1]);
        System.out.println("最大值：" + values[2]);
        System.out.println("平均值：" + values[3]);
        System.out.println("合计：" + values[4]);
        session.close();
    }

    /** 统计函数 */
    // select deptno,avg(sal) from Emp group by deptno having avg(sal)>2000
    // order by deptno;
    @Test
    public void test_statistics2() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("deptno"));
        projectionList.add(Projections.avg("sal"));
        projectionList.add(Projections.groupProperty("deptno"));
        criteria.setProjection(projectionList);
        // TODO having 怎么表示 暂时没有找到
        criteria.addOrder(Order.asc("deptno"));
        List<Object[]> list = criteria.list();

        for (Object[] arr : list) {
            System.out.println("部门编号：" + arr[0] + "\t" + "平均薪资：" + arr[1]);
        }
        session.close();
    }

    /** 使用函数(和底层数据库有关) */
    @Test
    public void test_function() {
        // TODO
    }

    /** 排序 */
    @Test
    public void test_orderby() {
        //SessionFactory sessionFactory = HibernateUtils.getSessionFactory();//方式一：没有整合Spring+Hibernate时，需要使用hibernate.cfg.xml手动创建
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();//方式二：整合Spring+Hibernate成功后，可以从Spring容器中直接获取Bean
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Emp.class);
        criteria.addOrder(Order.asc("job"));
        criteria.addOrder(Order.desc("sal"));
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
