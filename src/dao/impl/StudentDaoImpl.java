package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDao;
import entity.Student;

public class StudentDaoImpl implements StudentDao {
	private Connection conn=null;
	private PreparedStatement pst=null;
	private ResultSet rs=null;
	public List<Student> ShowAll() {
	List<Student> list=new ArrayList<Student>();
	try {
		//�������Ӷ���
		conn=getConn();
		//����Ҫִ�е�sql����
		String sql="select * from studentinfo";
		//����ִ�ж���
		pst=conn.prepareStatement(sql);
		// ��Ϊû��?�ţ���ֱ�ӵ��÷���ִ��
		rs = pst.executeQuery();
		// ����������������е����ݱ��浽������
		while (rs.next()) {
			Student flo=new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6));
			list.add(flo);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(rs, pst, conn);
	return list;
}
