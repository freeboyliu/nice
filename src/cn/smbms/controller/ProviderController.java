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

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;

//让这个类具备处理请求的功能
@Controller
//设置它的操作模块是供应商模块
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	
	//查询供应商列表
	@RequestMapping(value="/providerlist.html",method=RequestMethod.GET) 
	public String query(
			@RequestParam(name="queryProName",required = false) String proName,
	        @RequestParam(name="queryProCode",required = false) String proCode,
	        Model model
			) { 
		//创建保存数据的集合
		List<Provider> providerList=null;
		//将数据存到集合中
		providerList=providerService.getProviderList(proName, proCode);
		//将数据存放到model对象里面
		model.addAttribute("providerList", providerList);
		return "providerlist";
	}
	
	//转到增加页面的方法
	@RequestMapping(value="/provideradd.html",method=RequestMethod.GET)
	public String addProvider(@ModelAttribute("provider") Provider provider) {
		return "provideradd";
	}
	
	//增加供应商的方法
	@RequestMapping(value="/providersave.html",method=RequestMethod.POST)
	public String providerSave(Provider provider,HttpSession session) {
		//创建者id
		int createdBy=((User)session.getAttribute("user")).getId();
		//创建时间，保存到对象中
		provider.setCreatedBy(createdBy);
		provider.setCreationDate(new Date());
		//调用用户增加的方法
		boolean isOk=providerService.add(provider);
		if(isOk) {
			// 成功后页面上的数据也需要刷新
			return "redirect:providerlist.html";
		}
		return "provideradd";
	}
}
