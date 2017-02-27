package com.gorugoru.api.config.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	//private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	public CustomAccessDeniedHandler() {
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		String accept = request.getHeader("accept");
		String error = "true";
		String message = accessDeniedException.getMessage();
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("UTF-8");
		String data = "{\"response\":{\"error\":"+error+",\"message\":\""+ message+"\"}}";
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}

}
