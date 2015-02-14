package com.yuan.ssh.test.spring.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuan.ssh.hibernate.dao.BaseDAO;
import com.yuan.ssh.test.domain.Emp;
import com.yuan.ssh.test.spring.service.EmpServiceT;


@Transactional
//启用事务机制
@Service("empService")
public class EmpServiceTImpl implements EmpServiceT {

    @Resource
    private BaseDAO<Emp> baseDAO;

    @Override
    public void saveEmp(Emp emp) {
        baseDAO.save(emp);
    }

    @Override
    public void updateEmp(Emp emp) {
        baseDAO.update(emp);
    }

    @Override
    public Emp findEmpById(int id) {
        return baseDAO.get(Emp.class, id);
    }

    @Override
    public void deleteEmp(Emp emp) {
        baseDAO.delete(emp);
    }

    @Override
    public List<Emp> findAllList() {
        return baseDAO.find("from Emp");
    }
}