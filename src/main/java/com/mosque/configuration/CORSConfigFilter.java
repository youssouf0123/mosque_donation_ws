package com.mosque.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

//https://stackoverflow.com/questions/51719889/spring-boot-cors-issue

@EnableCaching
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSConfigFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(CORSConfigFilter.class);

	@Autowired
	private Environment environment;
	
//	@Value("${cors.url}")
//	private String corsUrl;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		logger.debug(String.format("Origin => %s", request.getHeader("Origin")));

		// todo: verify security of this approach
		// response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

		 response.setHeader("Access-Control-Allow-Origin", "*");

//		response.setHeader("Access-Control-Allow-Origin", corsUrl);

		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");

		// added Authorization Header
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}