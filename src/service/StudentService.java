package service;

import java.util.List;

import entity.Student;

/**
 * 业务操作类
 * 
 */
public interface StudentService {
	
	//查询所有数据的方法
	List<Student> selectAll();
}
