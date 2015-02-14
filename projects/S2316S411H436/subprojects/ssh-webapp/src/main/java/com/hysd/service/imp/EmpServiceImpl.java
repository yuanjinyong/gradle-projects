package com.hysd.service.imp;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hysd.domain.Emp;
import com.hysd.service.EmpService;
import com.yuan.ssh.hibernate.dao.BaseDAO;


@Transactional
//启用事务机制
@Service("empService")
public class EmpServiceImpl implements EmpService {

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