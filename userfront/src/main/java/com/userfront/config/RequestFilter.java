package com.userfront.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.userfront.enums.FilterHeadersEnum;

@Component
// This annotation means this filter is the first to be executed above all
// others in spring container, especially the spring security filter.
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.setHeader(FilterHeadersEnum.HEADER_ORIGIN.getmKey(), FilterHeadersEnum.HEADER_ORIGIN.getmValue());
		response.setHeader(FilterHeadersEnum.HEADER_METHODS.getmKey(), FilterHeadersEnum.HEADER_METHODS.getmValue());
		response.setHeader(FilterHeadersEnum.HEADER_ALLOW_HEADERS.getmKey(), FilterHeadersEnum.HEADER_ALLOW_HEADERS.getmValue());
		response.setHeader(FilterHeadersEnum.HEADER_MAX_AGE.getmKey(), FilterHeadersEnum.HEADER_MAX_AGE.getmValue());
		response.setHeader(FilterHeadersEnum.HEADER_ALLOW_CREDENTIALS.getmKey(), FilterHeadersEnum.HEADER_ALLOW_CREDENTIALS.getmValue());
		
		if(!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
			
			try{
				
				chain.doFilter(req, res);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			
			System.out.println("pre-flight");
			response.setHeader(FilterHeadersEnum.HEADER_SOME_METHODS.getmKey(), FilterHeadersEnum.HEADER_SOME_METHODS.getmValue());
			response.setHeader(FilterHeadersEnum.HEADER_MAX_AGE.getmKey(), FilterHeadersEnum.HEADER_MAX_AGE.getmValue());
			response.setHeader(FilterHeadersEnum.HEADER_ALLOW_HEADERS.getmKey(), FilterHeadersEnum.HEADER_ALLOW_HEADERS.getmValue());
			response.setStatus(HttpServletResponse.SC_OK);
			
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

}

