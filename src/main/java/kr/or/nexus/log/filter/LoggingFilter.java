package kr.or.nexus.log.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.or.nexus.log.dao.LogMapper;
import kr.or.nexus.vo.LogClassificationVO;
import kr.or.nexus.vo.LogVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
   
    @Autowired
    private LogMapper dao;
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        LogVO logVO = new LogVO();
        String projectId = null;
        HttpSession session = request.getSession();
        
//        if (!request.getRequestURI().startsWith("/nexus/resources") && !request.getRequestURI().startsWith("/nexus/alarm") && !request.getRequestURI().startsWith("/nexus/assets")) {
        if (!request.getRequestURI().startsWith("/nexus")) {
        	
        	try {
        	   MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
        	   String memberId = user.getMemberId();
        	   String groupId = user.getGroupMemberVO().getGroupId();
        	   log.info("사용자 아이디는: {}", memberId);
        	   log.info("사용자 그룹: {}", groupId);
        	   logVO.setMemberId(memberId);
        	   logVO.setGroupId(groupId);
        	}catch (Exception e) {
				// TODO: handle exception
			}
            log.info("Incoming request: method={}, URI={}", request.getMethod(), request.getRequestURI());
            
            String issueIdx = null;
            LogClassificationVO logConfirm = null;
            LogClassificationVO logClassificationVO = new LogClassificationVO();
            logClassificationVO.setClassAddress(request.getRequestURI());
            logClassificationVO.setClassMethod(request.getMethod());
          
            String uri = request.getRequestURI();
            String modifiedUri = uri;

            Map<String, String> patterns = new LinkedHashMap<>();
            patterns.put("CS\\d{3}", "CS%");
            patterns.put("PRJ\\d{3}", "PRJ%");
            patterns.put("GRP\\d{3}", "GRP%");
            patterns.put("ISS_\\d{3}", "ISS_%");

            for (Map.Entry<String, String> entry : patterns.entrySet()) {
                Pattern pattern = Pattern.compile(entry.getKey());
                Matcher matcher = pattern.matcher(modifiedUri);
                if (matcher.find()) {
                    if (entry.getKey().equals("ISS_\\d{3}")) {
                        issueIdx = matcher.group(0);
                    }
//                    if (entry.getKey().equals("PRJ\\d{3}")) {
//                        projectId = matcher.group(0);
//                    }
                    modifiedUri = matcher.replaceAll(entry.getValue());
                    logClassificationVO.setClassAddress(modifiedUri);
                    log.info("Modified URI: {}", modifiedUri);
                }
            }

            logVO.setLogSource(request.getRequestURI());
            logVO.setLogSourceModify(modifiedUri);
            logVO.setIssueIdx(issueIdx);

            try {
                logConfirm = dao.confirmLogClass(logClassificationVO);
                if (logConfirm == null) {
                    log.info("Adding new log classification.");
                    dao.insertLogClass(logClassificationVO);
                }
            } catch (Exception e) {
                log.error("Error occurred while fetching log classification: ", e);
            }
        }

        filterChain.doFilter(request, response);

//        if (!request.getRequestURI().startsWith("/nexus/resources") && !request.getRequestURI().startsWith("/nexus/alarm") && !request.getRequestURI().startsWith("/nexus/assets")) {
        if (!request.getRequestURI().startsWith("/nexus")) {

            log.info("Outgoing response: status={}", response.getStatus());
            
            try {
            	MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");	
            	if(user.getMemberId()!=null) {
            		logVO.setMemberId(user.getMemberId());
            	}
            	String viewName = (String) session.getAttribute("viewName");
            	log.info("### ttttt : " + viewName);
            	if(viewName.contains("tiles:")||viewName.contains("tiles3:")||viewName.contains("tiles4:")) {
            		session.removeAttribute("projectId");
            		log.info("Rendered view: {}", viewName);
            		log.info("프로젝트 아이디빠짐: {}", session.getAttribute("projectId")); 
            	}
            	projectId = (String) session.getAttribute("projectId");
            	logVO.setProjectId(projectId);
            	
            }catch (Exception e) {
            	// TODO: handle exception
            }finally {          	
            	Map<String, Object> params = new HashMap<>();
            	params.put("logVO", logVO);
            	params.put("classMethod", request.getMethod());
            	dao.insertLog(params);
            }
        }
    }
}
