package yycg.base.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.util.ResourcesUtil;
/**
 * 用户身份校验
 * @author dongwei
 * @date 2017年2月19日
 */
public class LoginInterceptor implements HandlerInterceptor {

	//进入action方法前执行(用户验证，授权)
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//校验用户身份是否合法
		HttpSession session = request.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		if (activeUser != null) {//用户已经登录
			return true;
		}
		//校验用户访问的是否是公开的资源
		List<String> open_urls = ResourcesUtil.getkeyList(Config.ANONYMOUS_ACTIONS);
		//用户访问的url
		String url = request.getRequestURI();
		for (String open_url : open_urls) {
			if (url.indexOf(open_url) >= 0) {//访问的是公开资源地址
				return true;
			}
		}
		//拦截用户，抛出异常
		ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 106, null));
		return false;
	}

	//进入actionfanfa，在返回modelAndView之前执行(统一对返回的数据进行处理，添加菜单，导航)
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	//action方法执行完成，已经返回modelAndView(统一处理系统异常，统一记录系统日志，监控action方法的执行时间)
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
