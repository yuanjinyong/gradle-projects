package com.yuan.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.yuan.domain.Emp;
import com.yuan.service.EmpService;


@Controller
public class LoginAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @Resource
    private EmpService empService;

    private String empname;
    private String password;

    public String login() {

        System.out.println("提交的请求参数如下：");
        System.out.println("empname：" + empname);
        System.out.println("password：" + password);

        int eid = 0;
        try {
            eid = Integer.parseInt(empname);
        } catch (Exception ex) {
        }

        Emp emp = empService.findEmpById(eid);// 为了测试结果，这里写死了
        if (emp != null) {
            System.out.println("根据主键ID查询记录：查到了，查询成功！");
            System.out.println(emp.toString());

            HttpServletRequest request = ServletActionContext.getRequest();// 在Struts2的Action中获取Servlet的原生API
            request.setAttribute("empname", emp.getEname());
            return SUCCESS;
        } else {
            System.out.println("根据主键ID查询记录：没查到，查询失败，记录不存在！");
            return "failure";
        }

    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }

}