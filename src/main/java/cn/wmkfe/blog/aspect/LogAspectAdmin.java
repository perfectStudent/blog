package cn.wmkfe.blog.aspect;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.SysLog;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.SysLogService;
import cn.wmkfe.blog.util.IpAdrressUtil;
import cn.wmkfe.blog.util.JacksonUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspectAdmin {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    /**
     * 定义切面
     */
    @Pointcut("execution(* cn.wmkfe.blog.controller.admin.*.*..*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        ReqeustLog reqeustLog = new ReqeustLog(
                request.getRequestURL().toString(),
                IpAdrressUtil.getIpAdrress(request),
                classMethod,
                joinPoint.getArgs()
        );
        logger.info("Rquest  ----- {}",reqeustLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("---------- doAfter 2 ----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAtfertRturning(JoinPoint joinPoint,Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            logger.info("Return ------ {}",result );
            return;
        }

        SysLog sysLog=new SysLog();
        //获取用户名

        sysLog.setUserName(user.getUserName());

        //获取用户ip地址
        sysLog.setIp(IpAdrressUtil.getIpAdrress(request));

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            String value = operation.value();
            sysLog.setOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = null;
        try {
//            params = JacksonUtil.obj2json(args);
            params = JSON.toJSONString(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysLog.setParams(params);
        //请求的时间
        sysLog.setCreateTime(new Date());

        sysLogService.saveSysLog(sysLog);

        logger.info("存入数据 ------ {}",sysLog );
        logger.info("返回数据 ------ {}",result );


    }


    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqeustLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
