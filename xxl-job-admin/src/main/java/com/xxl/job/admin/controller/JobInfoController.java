package com.xxl.job.admin.controller;

import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.core.thread.JobTriggerPoolHelper;
import com.xxl.job.admin.core.trigger.TriggerTypeEnum;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.model.User;
import com.xxl.job.admin.service.UserService;
import com.xxl.job.admin.service.UserTriggerService;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.admin.service.impl.UserTriggerServiceImpl;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xxl.job.core.biz.model.ReturnT.FAIL_CODE;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {

	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobService xxlJobService;
	@Autowired
	private UserTriggerService userTriggerServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	@RequestMapping
	public String index(Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

		// 枚举-字典
		model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());	// 路由策略-列表
		model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());								// Glue类型-字典
		model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());	// 阻塞处理策略-字典

		// 任务组
		List<XxlJobGroup> jobGroupList =  xxlJobGroupDao.findAll();
		model.addAttribute("JobGroupList", jobGroupList);
		model.addAttribute("jobGroup", jobGroup);

		return "jobinfo/jobinfo.index";
	}
//	@ResponseBody
	@RequestMapping("/xweb")
	public String xweb(String username){
		User user = userServiceImpl.selectByUsername(username);
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
			return "没有权限";
		}
		// 登录成功后重定向到首页
//		return new ModelAndView("redirect:/");
		return "/jobinfo";
	}

	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,  
			@RequestParam(required = false, defaultValue = "10") int length,
			int jobGroup, String jobDesc, String executorHandler, String filterTime) {

		return xxlJobService.pageList(start, length, jobGroup, jobDesc, executorHandler, filterTime);
	}

	@RequestMapping("/add")
	@ResponseBody
//	@RequiresPermissions("/usersPage")
	public ReturnT<String> add(XxlJobInfo jobInfo) {
		return xxlJobService.add(jobInfo);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(XxlJobInfo jobInfo) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		int userId = userTriggerServiceImpl.queryUserIdByTriggerId(jobInfo.getId());
		if (userId == user.getId()||SecurityUtils.getSubject().hasRole("3")){
			return xxlJobService.update(jobInfo);
		}else {
			return new ReturnT<String>(500,"没有修改权限");
		}

	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		Integer userId = userTriggerServiceImpl.queryUserIdByTriggerId(id);
		if (SecurityUtils.getSubject().hasRole("3")||(userId != null && userId.equals(user.getId()))){
			return xxlJobService.remove(id);
		}else {
			return new ReturnT<String>(500,"没有删除权限");
		}

	}
	
	@RequestMapping("/stop")		// TODO, pause >> stop
	@ResponseBody
	public ReturnT<String> pause(int id) {
		return xxlJobService.stop(id);
	}
	
	@RequestMapping("/start")		// TODO, resume >> start
	@ResponseBody
	public ReturnT<String> start(int id) {
		return xxlJobService.start(id);
	}
	
	@RequestMapping("/trigger")
	@ResponseBody
	//@PermessionLimit(limit = false)
	public ReturnT<String> triggerJob(int id, String executorParam) {
		// force cover job param
		if (executorParam == null) {
			executorParam = "";
		}

		JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam);
		return ReturnT.SUCCESS;
	}
	
}
