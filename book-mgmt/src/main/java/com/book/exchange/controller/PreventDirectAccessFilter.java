package com.book.exchange.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class PreventDirectAccessFilter implements Filter {

	@Value("${API_GATEWAY}")
	private String gatewayApi;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String message = "Requests only allowed through API Gateway !!! ";
		String url = gatewayApi + httpRequest.getServletPath();
		String hyperlink = "<a href=\"" + url + "\" style=\"color: red;\">Click here</a>";
		String finalMessage = "<html><body>" + hyperlink + "</body></html>";
		message = message + finalMessage;

		String gatewayOrigin = httpRequest.getHeader("origin");
		String gatewayId = httpRequest.getHeader("x-gateway-id");

		if (!(StringUtils.hasLength(gatewayOrigin) && gatewayOrigin.contains("9090"))
		        && !(StringUtils.hasLength(gatewayId) && gatewayId.contains("api-gateway"))) {
			httpResponse.sendError(401, message);
		}

		chain.doFilter(httpRequest, response);

	}

}