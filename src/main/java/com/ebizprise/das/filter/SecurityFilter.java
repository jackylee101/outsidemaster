package com.ebizprise.das.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class SecurityFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(SecurityFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final String srcIP = request.getRemoteAddr();
		// 例外:"139.162.70.24"
		final String startIp = "192.168.38.1";
		final String endIp = "192.168.38.254";
		final String localIp = "127.0.0.1";

		HttpServletResponse httpResp = null;

		if (response instanceof HttpServletResponse)
			httpResp = (HttpServletResponse) response;
		BigInteger addr = new BigInteger(InetAddress.getByName(srcIP)
				.getAddress());
		BigInteger low = new BigInteger(InetAddress.getByName(startIp)
				.getAddress());
		BigInteger high = new BigInteger(InetAddress.getByName(endIp)
				.getAddress());
		BigInteger local = new BigInteger(InetAddress.getByName(localIp)
				.getAddress());

		// if (low.compareTo(addr) <= 0 && addr.compareTo(high) <= 0) {
		if (low.compareTo(addr) <= 0 && addr.compareTo(high) <= 0) {
			// in range
			chain.doFilter(request, response);
		} else if (local.compareTo(addr) <= 0) {
			chain.doFilter(request, response);
		} else {
			// out of range
			chain.doFilter(request, response); // 先過
			// httpResp.sendError(HttpServletResponse.SC_BAD_REQUEST,
			// "That means goodbye forever!");
		}

	}

	@Override
	public void destroy() {

	}

}
