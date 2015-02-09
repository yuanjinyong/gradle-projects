package com.yuan.ssh.test.hibernate.associated;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.yuan.ssh.hibernate.utils.SessionFactoryUtils;
import com.yuan.ssh.test.domain.Customer;
import com.yuan.ssh.test.domain.Order;


/**
 * 测试SQL连接：交叉连接、内连接、左连接、右连接等
 */
public class TestAssociated {

    // 交叉连接(多表的笛卡儿积不常用)
    // select c.*,o.* from customers c, orders o
    @Test
    public void test_cross_join() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Customer c , Order o";
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            Customer customer = (Customer) obj[0];
            Order order = (Order) obj[1];
            System.out.println(i + 1 + "---" + customer);
            System.out.println(i + 1 + "---" + order);
        }
        session.close();
    }

    // 内连接
    // select c.* from customers c inner join orders o on c.id=o.customer_id
    // select o.* from orders o where o.customer_id=?
    @Test
    public void test_inner_join() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select c from Customer c inner join c.orders"; // inner
        // 可省略
        Query query = session.createQuery(hql);
        List<Customer> list = query.list();
        showCustomer(list);
        session.close();
    }

    // 左连接
    // select c.* from customers c left outer join orders o on
    // c.id=o.customer_id
    // select o.* from orders o where o.customer_id=?
    @Test
    public void test_left_outer_join() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select c from Customer c left outer join c.orders"; // outer
        // 可省略
        Query query = session.createQuery(hql);
        List<Customer> list = query.list();
        showCustomer(list);
        session.close();
    }

    // 右连接
    // select c.*, o.* from customers c right outer join orders o on
    // c.id=o.customer_id
    @Test
    public void test_right_outer_join() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Customer c right outer join c.orders"; // outer 可省略
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        for (int i = 0; list != null && i < list.size(); i++) {
            Object[] obj = list.get(i);
            Customer customer = (Customer) obj[0];
            Order order = (Order) obj[1];
            System.out.println(i + 1 + "---" + customer);
            System.out.println(i + 1 + "---" + order);
        }
        session.close();
    }

    // 迫切内连接(推荐)
    // select c.*,o.* from customers c inner join orders o on c.id=o.customer_id
    @Test
    public void test_inner_join_fetch() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select c from Customer c inner join fetch c.orders"; // inner
        // 可省略
        Query query = session.createQuery(hql);
        query.setCacheable(true); // 设置缓存
        List<Customer> list = query.list();
        showCustomer(list);
        session.close();
    }

    // 迫切左外连接(推荐)
    // select c.*,o.* from customers c left outer join orders o on
    // c.id=o.customer_id
    @Test
    public void test_left_outer_join_fetch() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select c from Customer c left outer join fetch c.orders"; // outer可省略
        Query query = session.createQuery(hql);
        query.setCacheable(true); // 设置缓存
        List<Customer> list = query.list();
        showCustomer(list);
        session.close();
    }

    // 迫切左外连接(推荐)
    // select c.*, o.* from customers c left outer join orders o on
    // c.id=o.customer_id
    @Test
    public void test_left_outer_join_fetch_QBC() {
        SessionFactory sessionFactory = SessionFactoryUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Customer.class).setFetchMode("orders", FetchMode.JOIN);
        criteria.setCacheable(true); // 设置缓存
        List<Customer> list = criteria.list();
        showCustomer(list);
        session.close();
    }

    // 打印Order信息
    private void showCustomer(List<Customer> list) {
        for (Customer customer : list) {
            System.out.println(customer);
            if (customer.getOrders() != null && customer.getOrders().size() > 0) {
                Set<Order> orders = customer.getOrders();
                for (Iterator<Order> it = orders.iterator(); it.hasNext();) {
                    Order order = it.next();
                    System.out.println(order);
                }
            }
        }
    }

}
