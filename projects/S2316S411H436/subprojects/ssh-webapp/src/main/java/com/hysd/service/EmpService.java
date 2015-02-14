package com.hysd.service;

import java.util.List;

import com.hysd.domain.Emp;

public interface EmpService {

	public void saveEmp(Emp emp);

	public void updateEmp(Emp emp);

	public Emp findEmpById(int id);

	public void deleteEmp(Emp emp);

	public List<Emp> findAllList();

}