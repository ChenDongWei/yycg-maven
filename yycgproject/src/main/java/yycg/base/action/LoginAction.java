package yycg.base.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.UserService;

@Controller
public class LoginAction {
	@Autowired
	private UserService userService;
	
	//用户登录界面
	@RequestMapping("/login")
	public String login(Model model) throws Exception{
		//数据获取传到页面
		
		
		return "/base/login";
	}
	
	//用户提交登陆
	@RequestMapping("/loginsubmit")
	public @ResponseBody SubmitResultInfo loginSubmit(HttpSession session, String userid, String pwd, String validateCode) throws Exception{
		//验证码验证
		String validateCode_session = (String) session.getAttribute("validateCode");
		if (validateCode_session != null && !validateCode_session.equals(validateCode)) {//验证码错误
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		}
		
		//service用户认证
		ActiveUser activeUser = userService.checkUserInfo(userid, pwd);
		//将用户身份写入session
		session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, new Object[]{""}));
	}
	
	//退出系统
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		//session过期
		session.invalidate();
		return "redirect:login.action";
	}
}
