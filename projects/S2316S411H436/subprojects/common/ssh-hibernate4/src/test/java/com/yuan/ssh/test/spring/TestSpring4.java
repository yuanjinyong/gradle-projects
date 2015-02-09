package com.yuan.ssh.test.spring;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yuan.ssh.test.domain.Emp;
import com.yuan.ssh.test.spring.service.EmpService;


/**
 * 测试Spring的配置是否正确
 */
public class TestSpring4 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 测试Spring+Hibernate的整合配置是否成功

        String configLocation = "classpath:applicationContext.xml";// Spring配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);

        EmpService empService = (EmpService) ctx.getBean("empService");

        // 示例：查询单个
        try {
            Emp emp = empService.findEmpById(1001);

            if (emp != null) {
                System.out.println("查询单个：查到了，查询成功！");
                System.out.println(emp.toString());
            } else {
                System.out.println("查询单个：没查到，查询失败，记录不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 示例：查询所有
        try {
            List<Emp> empList = empService.findAllList();
            if (empList != null && !empList.isEmpty()) {
                System.out.println("查询所有：查询成功，共有" + empList.size() + "条记录！");
                for (Emp e : empList) {
                    System.out.println(e.toString());
                }
            } else {
                System.out.println("查询所有：查询失败，没有任何记录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //		// 示例：保存
        //		try {
        //			Emp emp = new Emp();
        //			emp.setEmpno(1001);// 主键ID，不可重复
        //			emp.setEname("员工" + emp.getEmpno());
        //			emp.setJob("IT" + emp.getEmpno());
        //			emp.setHiredate(new java.util.Date());
        //			emp.setSal(10000D);
        //			emp.setComm(1500D);
        //			emp.setDeptno(10);
        //			// 保存
        //			empService.saveEmp(emp);
        //			System.out.println("保存成功");
        //		} catch (Exception e) {
        //			e.printStackTrace();
        //		}
        //
        //		// 示例：修改（先查询出来，再进行修改）
        //		try {
        //			Emp emp = empService.findEmpById(1001);
        //
        //			if (emp != null) {
        //				emp.setSal(emp.getSal() + 1000D);
        //				// 保存
        //				empService.updateEmp(emp);
        //				System.out.println("修改成功");
        //			} else {
        //				System.out.println("查询失败，无法修改");
        //			}
        //		} catch (Exception e) {
        //			e.printStackTrace();
        //		}

    }

}
