package cn.wmkfe.blog.handler;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AdminLoginHandlerInterceptor implements HandlerInterceptor {
    //目标方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object admin = request.getSession().getAttribute("user");
        if (admin==null){
            request.getRequestDispatcher("/404.html").forward(request,response);
            return false;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    //判断是否是ajax请求
    private boolean isAjax(HttpServletRequest request){//判断request是否是ajax请求
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equalsIgnoreCase( request.getHeader("X-Requested-With").toString()) );
    }


    protected void writeJson(HttpServletResponse response, Map<String,Object> error){
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject json=new JSONObject(error);
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
