package com.xxl.job.admin.controller;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {

	@Resource
	private XxlJobService xxlJobService;
//	@Autowired
//	private ShiroService shiroService;

	@RequestMapping("/")
	public String index(Model model) {
//		shiroService.updatePermission();
		Map<String, Object> dashboardMap = xxlJobService.dashboardInfo();
		model.addAllAttributes(dashboardMap);

		return "index";
	}

    @RequestMapping("/chartInfo")
	@ResponseBody
	public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate) {
        ReturnT<Map<String, Object>> chartInfo = xxlJobService.chartInfo(startDate, endDate);
        return chartInfo;
    }
	@RequestMapping("/checkPermission")
	@ResponseBody
	public Map<String,String> checkPermission() {
		Map<String,String> map = new HashMap<>();
		try{
			SecurityUtils.getSubject().checkRole("1");
		}catch (Exception e){
			e.printStackTrace();
			map.put("flag","true");
			return map;
		}
		map.put("flag","false");
		return map;
	}
	@RequestMapping("/toLogin")
	@PermessionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		Session session = SecurityUtils.getSubject().getSession();
		if (session.getAttribute("userSession") != null) {
			return "redirect:/";
		}
		return "login";
	}
	/**
	 * shiro框架登录
	 * @param user
	 */
	@ResponseBody
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public ReturnT<String>  login(User user){
		// 表面校验
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
		}
		// 获取主体
		Subject subject = SecurityUtils.getSubject();
		try{
			// 调用安全认证框架的登录方法
			subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
		}catch(AuthenticationException ex){
			new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
			return ReturnT.FAIL;
		}
		// 登录成功后重定向到首页
//		return new ModelAndView("redirect:/");
		return ReturnT.SUCCESS;
	}
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	public ReturnT<String> logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping("/help")
	public String help() {

		/*if (!PermissionInterceptor.ifLogin(request)) {
			return "redirect:/toLogin";
		}*/

		return "help";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
