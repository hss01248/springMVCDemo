package com.hss01248.springdemo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object handler, Exception ex) {

        ex.printStackTrace();
        String msg = null;
        CustomException customException = null;

        if (ex instanceof  CustomException){
            customException = (CustomException) ex;
        }else {
            customException = new CustomException("发生了错误:"+ex.getMessage());
        }

        msg = customException.getMessage();

        httpServletRequest.setAttribute("msg",msg);


        try {
			httpServletRequest.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(httpServletRequest,httpServletResponse);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new ModelAndView();
    }
}
