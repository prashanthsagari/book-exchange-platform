package com.book.exchange.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
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

		String gatewayId = httpRequest.getHeader("X-Gateway-Id");
		String gatewayPath = httpRequest.getHeader("X-Gateway-Path");

		if (!"/api-gateway".equals(gatewayPath) && !"api-gateway".equals(gatewayId)) {
			httpResponse.sendError(401, message);
		}

		chain.doFilter(httpRequest, response);

	}

}
