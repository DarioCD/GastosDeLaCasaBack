package com.gasto.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticatonFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AuthCredentials autheCredentials = new AuthCredentials();

		try {
			autheCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
		} catch (Exception e) {
		}

		UsernamePasswordAuthenticationToken usernamePath = new UsernamePasswordAuthenticationToken(
				autheCredentials.getEmail(), autheCredentials.getPassword(), Collections.emptyList());

		return getAuthenticationManager().authenticate(usernamePath);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

		String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());

		response.addHeader("AuthorizationR", "Bearer " + token);

		response.getWriter().flush();

		super.successfulAuthentication(request, response, chain, authResult);
	}
}