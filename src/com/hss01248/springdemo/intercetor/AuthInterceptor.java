package com.hss01248.springdemo.intercetor;

import com.hss01248.springdemo.bean.BaseNetBean;
import com.hss01248.springdemo.util.MyJSON;
import com.hss01248.springdemo.util.MyLog;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class AuthInterceptor implements HandlerInterceptor {

    //需要校验的url列表
    private  static String[] urls = {
            "/test/getStandardJson.json",
            "/test/postCommonJson.json"

    };
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //先判断该url是否为需要校验登录状态的url:遍历list,一个个去判断是否包含.
        String url = httpServletRequest.getRequestURL().toString();

        boolean letGo = true;
        for(String target : urls){
            if (url.contains(target)){
                letGo = false;
                break;
            }
        }
        if(letGo){
            return true;
        }

        Object attr = httpServletRequest.getAttribute("sessionId");
        if(attr == null){
            httpServletResponse.setCharacterEncoding("utf-8");
            String str = MyJSON.toJsonStr(new BaseNetBean(5,"未登录",null));
            MyLog.e(str);
            httpServletResponse.getWriter().write(str);
            return false;
        }
        String sessionId = (String)attr;
        if(!sessionId.equalsIgnoreCase(httpServletRequest.getRequestedSessionId())){
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().write(MyJSON.toJsonStr(new BaseNetBean(6,"登录过期,请重新登录",null)));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
