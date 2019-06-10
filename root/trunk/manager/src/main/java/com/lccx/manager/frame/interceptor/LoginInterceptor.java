//package com.lccx.manager.frame.interceptor;
//
//import com.lccx.manager.entity.SysUser;
//import com.lccx.manager.frame.ConstantClass;
//import com.lccx.manager.util.Util;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//
////    @Resource
////    private JedisPool jedisPool;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        SysUser user=(SysUser)session.getAttribute(ConstantClass.SESSION_USER);
//        if(Util.isCon(user)){
//            if(Util.isCon(user.getName())){
//                //request.getRequestDispatcher("../loginController/index.action").forward(request, response);
////                if()
//                return true;
//            }
//        }
//        request.getRequestDispatcher("/Login").forward(request, response);
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
