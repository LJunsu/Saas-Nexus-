package kr.or.nexus.log.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewNameInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(ViewNameInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.getViewName() != null) {
        	logger.info("ViewName set in request attribute: {}", modelAndView.getViewName());
        	HttpSession session = request.getSession();
            session.removeAttribute("viewName");
            session.setAttribute("viewName", modelAndView.getViewName());
        } else {
//            logger.warn("ModelAndView or ViewName is null, cannot set viewName");
        }
    }
}
