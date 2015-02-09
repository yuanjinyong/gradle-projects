package com.yuan.ssh.test.hibernate.hql;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.yuan.ssh.hibernate.utils.SessionFactoryUtils;
import com.yuan.ssh.test.domain.Emp;


/**
 * 测试HQL查询
 */
public class TestHql {

    /** 查询全部 （推荐） */
    @Test
    public void test_list() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp";
        Query query = session.createQuery(hql);
        query.setCacheable(true); // 设置缓存
        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 查询全部 */
    // 需要为<class name="Emp" lazy="false">...</class>添加lazy="false"，测试才通过
    @Test
    public void test_iterator() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp";
        Query query = session.createQuery(hql);
        Iterator<Emp> it = query.iterate();
        while (it.hasNext()) {
            Emp emp = it.next();
            System.out.println(emp);
        }
        session.close();
    }

    /** 分页查询 */
    // select * from
    // ( select e.*, rownum rn from ( select * from emp ) e where rownum <= 10 )
    // where rn > 1
    @Test
    public void test_pagination() {
        int pageNo = 1; // 当前页数
        int pageSize = 5; // 每页显示条数

        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp";
        Query query = session.createQuery(hql);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        query.setCacheable(true); // 设置缓存
        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 查询结果是唯一值 */
    @Test
    public void test_uniqueResult() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp where empno=7369";
        Query query = session.createQuery(hql);
        Emp emp = (Emp) query.uniqueResult();
        System.out.println(emp);
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection1() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select empno from Emp"; // 查询单值
        Query query = session.createQuery(hql);
        List<Integer> list = query.list();
        for (Integer empno : list) {
            System.out.println(empno);
        }
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection2() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select empno,ename,hiredate,sal from Emp"; // 查询多值
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        for (Object[] arr : list) {
            System.out.println(arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3]);
        }
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection3() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select new Emp(empno,ename,hiredate,sal) from Emp"; // 查询多值
        // 封装在一个对象中
        Query query = session.createQuery(hql);
        List<Emp> list = query.list();
        for (Emp emp : list) {
            System.out.println(emp.getEmpno() + "\t" + emp.getEname() + "\t" + emp.getHiredate() + "\t" + emp.getSal());
        }
        session.close();
    }

    /** 投影查询 */
    @Test
    public void test_projection4() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select new Map(empno,ename,hiredate,sal) from Emp"; // map
        // 大小都都可以，
        // 查询多值
        // 封装在一个Map中
        Query query = session.createQuery(hql);
        List<Map> list = query.list();
        for (Map map : list) {
            System.out.println(map.get("0") + "\t" + map.get("1") + "\t" + map.get("2") + "\t" + map.get("3"));
        }
        session.close();
    }

    /** 投影查询 （使用别名） */
    @Test
    public void test_projection5() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select new Map(e.empno as empno ,e.ename as ename ,e.hiredate as hiredate ,e.sal as sal) from Emp as e";
        Query query = session.createQuery(hql);
        List<Map> list = query.list();
        for (Map map : list) {
            System.out.println(map.get("empno") + "\t" + map.get("ename") + "\t" + map.get("hiredate") + "\t"
                    + map.get("sal"));
        }
        session.close();
    }

    /** 去掉重复值 */
    @Test
    public void test_distinct() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select distinct job from Emp";
        Query query = session.createQuery(hql);
        List<String> list = query.list();
        for (String job : list) {
            System.out.println(job);
        }
        session.close();
    }

    /** 条件查询 */
    @Test
    public void test_where1() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        // String hql = "from Emp where empno=7369 and ename='SMITH'";
        // String hql = "from Emp where empno=7369 or ename='ALLEN'";
        // String hql = "from Emp where not (ename='SMITH')"; // () 可以不写

        // String hql = "from Emp where sal between 3000 and 5000)";
        // String hql = "from Emp where sal not between 3000 and 5000)";

        // String hql = "from Emp where empno in(7369,7900,7654)";
        // String hql = "from Emp where empno not in(7369,7900,7654)";

        // String hql = "from Emp where ename like 'S%'";
        // String hql = "from Emp where ename not like 'S%'";

        // String hql="from Emp where comm is null";
        // String hql="from Emp where comm is not null";

        // String hql = "from Emp where sal >= 3000 and sal<= 5000)";
        // String hql="from Emp where sal <>3000";
        String hql = "from Emp where sal !=3000";

        Query query = session.createQuery(hql);
        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 条件查询 */
    @Test
    public void test_where2() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "from Emp where  ename like ?";
        Query query = session.createQuery(hql);
        query.setString(0, "J%");

        // String hql = "from Emp where  ename like :ename";
        // Query query = session.createQuery(hql);
        // query.setString("ename", "J%");

        List<Emp> list = query.list();
        show(list);
        session.close();
    }

    /** 统计函数 */
    @Test
    public void test_statistics1() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select count(*),min(sal),max(sal),avg(sal),sum(sal) from Emp";
        Query query = session.createQuery(hql);
        Object[] values = (Object[]) query.uniqueResult();
        System.out.println("总数：" + values[0]);
        System.out.println("最小值：" + values[1]);
        System.out.println("最大值：" + values[2]);
        System.out.println("平均值：" + values[3]);
        System.out.println("合计：" + values[4]);
        session.close();
    }

    /** 统计函数 */
    @Test
    public void test_statistics2() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select deptno,avg(sal)  from Emp group by deptno having avg(sal)>2000 order by deptno";
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        for (Object[] arr : list) {
            System.out.println("部门编号：" + arr[0] + "\t" + "平均薪资：" + arr[1]);
        }
        session.close();
    }

    /** 使用函数(和底层数据库有关) */
    @Test
    public void test_function() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select date_format(hiredate,'%Y-%m-%d') from Emp";// MySQL：date_format和str_to_date
        //String hql = "select to_char(hiredate,'yyyy-mm-dd') from Emp";// Oracle：to_char和to_date
        //String hql = "select convert(varchar(20), hiredate, 23) from Emp";// SQL Server：convert
        Query query = session.createQuery(hql);
        List<String> list = query.list();
        for (String ename : list) {
            System.out.println(ename);
        }
        session.close();
    }

    /** 排序 */
    @Test
    public void test_orderby() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Emp order by job,sal desc";
        Query query = session.createQuery(hql);
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
