package com.hss01248.springdemo.intercetor;

import com.hss01248.springdemo.util.MyLog;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletRequest.setAttribute("httpStartTimessss",System.nanoTime());


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
       Object attr = httpServletRequest.getAttribute("httpStartTimessss");
       if(attr != null){
           Long startTime = (Long) attr;
           long time = System.nanoTime() -startTime;
           int ms = (int) (time/1000000);
           long ns = time % 1000000;

           MyLog.d("the request "+httpServletRequest.getRequestURL() +"take server time:"+ms+"毫秒"+ns+"纳秒");
       }



    }
}
