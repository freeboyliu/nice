package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Student;
import service.StudentService;
@WebServlet("/Index")
public class IndextServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�ַ���������
		req.setCharacterEncoding("UTF-8");
		//����һ��service���󣬲����÷���
		StudentService stu=new StudentService();
		//��һ�����������淵�ز�ѯ������������
		List<Student> stulist=stu.ShowAll();
		//����ȡ����ֵ���浽session��
		HttpSession session = req.getSession();
		session.setAttribute("stulist", stulist);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
