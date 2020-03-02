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
		//字符编码问题
		req.setCharacterEncoding("UTF-8");
		//创建一个service对象，并调用方法
		StudentService stu=new StudentService();
		//用一个集合来保存返回查询到的所有数据
		List<Student> stulist=stu.ShowAll();
		//将获取到的值保存到session中
		HttpSession session = req.getSession();
		session.setAttribute("stulist", stulist);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
