package com.yuan.ssh.test.hibernate.sql;


import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Test;

import com.yuan.ssh.hibernate.utils.SessionFactoryUtils;
import com.yuan.ssh.test.domain.Emp;


/**
 * 测试SQL查询
 */
public class TestSql {

    /** 查询全部 */
    @Test
    public void test_sql1() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String sql = "select * from emp";
        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> list = query.list();
        for (Object[] arr : list) {
            System.out.println(arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3] + "\t" + arr[4] + "\t" + arr[5]
                    + "\t" + arr[6] + "\t" + arr[7]);
        }
        session.close();
    }

    /** 查询全部 */
    @Test
    public void test_sql2() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String sql = "select * from emp";
        SQLQuery query = session.createSQLQuery(sql);
        query.addScalar("empno", StandardBasicTypes.INTEGER);
        query.addScalar("ename", StandardBasicTypes.STRING);
        query.addScalar("job", StandardBasicTypes.STRING);
        query.addScalar("mgr", StandardBasicTypes.INTEGER);
        query.addScalar("hiredate", StandardBasicTypes.DATE);
        query.addScalar("sal", StandardBasicTypes.DOUBLE);
        query.addScalar("comm", StandardBasicTypes.DOUBLE);
        query.addScalar("deptno", StandardBasicTypes.INTEGER);
        List<Object[]> list = query.list();
        for (Object[] arr : list) {
            System.out.println(arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3] + "\t" + arr[4] + "\t" + arr[5]
                    + "\t" + arr[6] + "\t" + arr[7]);
        }
        session.close();
    }

    /** 查询全部 */
    @Test
    public void test_sql3() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String sql = "select * from emp";
        SQLQuery query = session.createSQLQuery(sql);
        query.addScalar("empno", StandardBasicTypes.INTEGER);
        query.addScalar("ename", StandardBasicTypes.STRING);
        query.addScalar("job", StandardBasicTypes.STRING);
        query.addScalar("mgr", StandardBasicTypes.INTEGER);
        query.addScalar("hiredate", StandardBasicTypes.DATE);
        query.addScalar("sal", StandardBasicTypes.DOUBLE);
        query.addScalar("comm", StandardBasicTypes.DOUBLE);
        query.addScalar("deptno", StandardBasicTypes.INTEGER);
        query.setResultTransformer(Transformers.aliasToBean(Emp.class));
        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 查询全部 (推荐) */
    @Test
    public void test_sql4() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        String sql = "select * from emp";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Emp.class);

        // String sql = "select {e.*} from emp e";
        // SQLQuery query = session.createSQLQuery(sql);
        // query.addEntity("e", Emp.class);
        query.setCacheable(true); // 设置缓存
        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 条件查询 */
    @Test
    public void test_where() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        String sql = "select * from emp where  ename like ?";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Emp.class);
        query.setString(0, "J%");

        // String sql = "select * from emp where  ename like :ename";
        // SQLQuery query = session.createSQLQuery(sql);
        // query.addEntity(Emp.class);
        // query.setString("ename", "J%");

        List<Emp> list = query.list();
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
