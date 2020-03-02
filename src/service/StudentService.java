package service;

import java.util.List;

import dao.StudentDao;
import entity.Student;

public class StudentService {
	private StudentDao stud=new StudentDao();

	public List<Student> ShowAll() {
		List<Student> stu=stud.ShowAll();
		if(stu!=null)
		{
			return stu;
		}
		else {
			return null;
		}
	}
}
