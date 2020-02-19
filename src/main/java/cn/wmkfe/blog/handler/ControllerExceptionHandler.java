package cn.wmkfe.blog.handler;


import cn.wmkfe.blog.NotFoundException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception {
        logger.error("Request URL : {} Exception : {}",request.getRequestURI(),e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        if(isAjax(request)){
            Map<String,Object> map=new HashMap<>();
            map.put("code",500);
            map.put("msg", "服务器内部错误!");
            writeJson(response,map);
            return null;
        }else{
            ModelAndView mv=new ModelAndView();
            mv.addObject("code",500);
            mv.addObject("msg", "服务器内部错误!");
            mv.setViewName("error/500");
            return mv;
        }

    }
    //未找到相关内容异常
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundException(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception {
        logger.error("Request URL : {} Exception : {}",request.getRequestURI(),e);
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
//            throw e;
//        }
        if(isAjax(request)){
            Map<String,Object> map=new HashMap<>();
            map.put("code",404);
            map.put("msg", e.getMessage());
            writeJson(response,map);
            return null;
        }else{
            ModelAndView mv=new ModelAndView();
            mv.addObject("code",404);
            mv.addObject("msg", e.getMessage());
            mv.setViewName("error/404");
            return mv;
        }

    }

    //jsr303校验异常捕获
    @ExceptionHandler(BindException.class)
    public ModelAndView bindExceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception {

        BindException bindException = (BindException)e;
        List<ObjectError> allErrors = bindException.getAllErrors();
        StringBuffer errorMsg=new StringBuffer();
        for (ObjectError error: allErrors) {
            errorMsg.append(error.getDefaultMessage()+"<br/>");
        }
        System.out.println(errorMsg);
        logger.error("Request URL : {} Exception : {}",request.getRequestURI(),e);
        if(isAjax(request)){
            Map<String,Object> map=new HashMap<>();
            map.put("code",500);
            map.put("msg", errorMsg);
            writeJson(response,map);
            return null;
        }else{
            ModelAndView mv=new ModelAndView();
            mv.addObject("code",500);
            mv.addObject("msg", "服务器内部错误!");
            mv.setViewName("error/500");
            return mv;
        }

    }
    //mysql异常
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView integrityExceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception {
        logger.error("Request URL : {} Exception : {}",request.getRequestURI(),e);
        if(isAjax(request)){
            Map<String,Object> map=new HashMap<>();
            map.put("code",500);
            map.put("msg", "数据库操作异常");
            writeJson(response,map);
            return null;
        }else{
            ModelAndView mv=new ModelAndView();
            mv.addObject("code",500);
            mv.addObject("msg", "数据库操作异常!");
            mv.setViewName("error/500");
            return mv;
        }

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
