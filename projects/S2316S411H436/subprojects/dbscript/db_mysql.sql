drop table if exists  orders;
drop table if exists  customers;
drop table if exists  emp;

create table customers
(
    id   int(11),
    name varchar(255),
    primary key(id)
);

create table orders
(
    id          int(11),
    orderNumber varchar(255),
    customer_id int(11),
    primary key(id),
    foreign key (customer_id) references customers(id)
);

create table emp
(
  empno    int(11),
  ename    varchar(10),
  job      varchar(9),
  mgr      int(11),
  hiredate date,
  sal      double,
  comm     double,
  deptno   int(11),
  primary key(empno)
);


#添加测试数据
INSERT INTO customers(id,NAME) VALUES(1,'Tom');
INSERT INTO customers(id,NAME) VALUES(2,'Jerry');
INSERT INTO customers(id,NAME) VALUES(3,'Kingkong');
INSERT INTO customers(id,NAME) VALUES(4,'Simpleit');
INSERT INTO customers(id,NAME) VALUES(5,'test_saveOrUpdate2');
INSERT INTO customers(id,NAME) VALUES(6,'test_update');
INSERT INTO customers(id,NAME) VALUES(7,'test_update_hql');
INSERT INTO customers(id,NAME) VALUES(8,'test_delete');
INSERT INTO customers(id,NAME) VALUES(9,'test_delete_hql');
INSERT INTO customers(id,NAME) VALUES(10,'test2_saveOrUpdate2');
INSERT INTO customers(id,NAME) VALUES(11,'test2_update');
INSERT INTO customers(id,NAME) VALUES(12,'test2_update_hql');
INSERT INTO customers(id,NAME) VALUES(13,'test2_delete');
INSERT INTO customers(id,NAME) VALUES(14,'test2_delete_hql');

INSERT INTO orders(id,orderNumber,customer_id) VALUES(1,'Tom_Order_1',1);
INSERT INTO orders(id,orderNumber,customer_id) VALUES(2,'Tom_Order_2',1);
INSERT INTO orders(id,orderNumber,customer_id) VALUES(3,'Jerry_Order_1',2);
INSERT INTO orders(id,orderNumber,customer_id) VALUES(4,'Kingkong_Order_1',3);
INSERT INTO orders(id,orderNumber,customer_id) VALUES(5,'Simpleit_Order_1',4);
INSERT INTO orders(id,orderNumber,customer_id) VALUES(6,'Unkonwn_Order',NULL);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7369, 'SMITH', 'CLERK', 7902, STR_TO_DATE('17-12-1980', '%d-%m-%Y'), 800.00, NULL, 20);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7499, 'ALLEN', 'SALESMAN', 7698, STR_TO_DATE('20-02-1981', '%d-%m-%Y'), 1600.00, 300.00, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7521, 'WARD', 'SALESMAN', 7698, STR_TO_DATE('22-02-1981', '%d-%m-%Y'), 1250.00, 500.00, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7566, 'JONES', 'MANAGER', 7839, STR_TO_DATE('02-04-1981', '%d-%m-%Y'), 2975.00, NULL, 20);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7654, 'MARTIN', 'SALESMAN', 7698, STR_TO_DATE('28-09-1981', '%d-%m-%Y'), 1250.00, 1400.00, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7698, 'BLAKE', 'MANAGER', 7839, STR_TO_DATE('01-05-1981', '%d-%m-%Y'), 2850.00, NULL, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7782, 'CLARK', 'MANAGER', 7839, STR_TO_DATE('09-06-1981', '%d-%m-%Y'), 2450.00, NULL, 10);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7788, 'SCOTT', 'ANALYST', 7566, STR_TO_DATE('19-04-1987', '%d-%m-%Y'), 3000.00, NULL, 20);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7839, 'KING', 'PRESIDENT', NULL, STR_TO_DATE('17-11-1981', '%d-%m-%Y'), 5000.00, NULL, 10);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7844, 'TURNER', 'SALESMAN', 7698, STR_TO_DATE('08-09-1981', '%d-%m-%Y'), 1500.00, 0.00, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7876, 'ADAMS', 'CLERK', 7788, STR_TO_DATE('23-05-1987', '%d-%m-%Y'), 1100.00, NULL, 20);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7900, 'JAMES', 'CLERK', 7698, STR_TO_DATE('03-12-1981', '%d-%m-%Y'), 950.00, NULL, 30);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7902, 'FORD', 'ANALYST', 7566, STR_TO_DATE('03-12-1981', '%d-%m-%Y'), 3000.00, NULL, 20);

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
VALUES (7934, 'MILLER', 'CLERK', 7782, STR_TO_DATE('23-01-1982', '%d-%m-%Y'), 1300.00, NULL, 10);
