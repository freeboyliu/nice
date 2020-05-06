package cn.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
//让这个类具备处理请求的功能
@Controller
//设置它的操作模块是订单模块
@RequestMapping("/bill")
public class BillController {
	//创建业务层对象
	@Autowired
	private BillService billService;
	 
	//第一次查询订单列表
	@RequestMapping(value="/billlist.html",method=RequestMethod.GET)
	public String query(
			@RequestParam(name="queryProductName",required = false) String productName,
			@RequestParam(name="queryProviderId",required = false) String providerId,
			@RequestParam(name="queryIsPayment",required = false) String isPayment,
			Model model
			) {
		//创建保存数据的集合
		List<Bill> billList=null;
		//将数据存到集合中
		billList=billService.getBillList(productName,providerId,isPayment);
		//将集合存到model对象中
		model.addAttribute("billList", billList);
		return "billlist";
	}
	
	//转到订单增加页面
	@RequestMapping(value="/billadd.html",method=RequestMethod.GET)
	public String addBill(@ModelAttribute("bill") Bill bill) {
		return "billadd";
	}
	
	//增加订单的方法
	@RequestMapping(value="/billsave.html",method=RequestMethod.POST)
	public String billSave(Bill bill,HttpSession session) {
		//获取创建者id
		int createdBy=((User)session.getAttribute("user")).getId();
		//创建时间
		bill.setCreatedBy(createdBy);
		bill.setCreationDate(new Date());
		//调用增加的方法
		boolean isOk=billService.add(bill);
		if(isOk) {
			return "redirect:billlist.html";
		}
		return "billadd";
	}
}
