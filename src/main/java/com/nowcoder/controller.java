package com.nowcoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import sun.misc.Contended;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Shirley on 2017/7/11.
 */
@Controller
public class controller {
    @Autowired //IoC 依赖注入(通过注解或者配置文件)  实现与应用分开
    private ToutiaoService toutiaoService;
    private static final Logger logger = LoggerFactory.getLogger(controller.class);

    @RequestMapping("/")
    @ResponseBody
    public String news(HttpSession session){
        logger.info("visit news");

        return "hello nowcoder,"+session.getAttribute("msg")+toutiaoService.say();



    }
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headerName=request.getHeaderNames();
        while(headerName.hasMoreElements()){
            String name=headerName.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        for(Cookie cookie:request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
        }
        sb.append(request.getMethod());

        return sb.toString();

    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value="nowcoderid",defaultValue = "a") String nowcoderId,
                           @RequestParam(value="key",defaultValue = "key") String key,
                           @RequestParam(value = "value",defaultValue = "value") String value,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);

        return "NowCoderId From Cookie:"+nowcoderId;

    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
        /*RedirectView red=new RedirectView("/response",true);
        if(code==302){
            red.setStatusCode(HttpStatus.MOVED_TEMPORARILY);//永久性转移 from cache
        }

        return red;
        */
        session.setAttribute("msg","Jump from redirect");
        return "redirect:/";//默认302跳转
    }

    @RequestMapping("/admin")
    @ResponseBody
    public  String admin(@RequestParam(value = "key",required = false) String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){//定义函数处理error
        return "error"+e.getMessage();
    }

}
