package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UsuarioRepository userRepository;
	
	public AuthenticationByTokenFilter(TokenService tokenService, UsuarioRepository userRepository) {
		super();
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = retriveToken(request);
		boolean valid = tokenService.isTokenValid(token);
		
		if(valid) authenticateClient(token); 
		
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = userRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String retriveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null  || token.isEmpty() || !token.startsWith("Bearer ")) return null; 
			
		return token.substring(7, token.length());
	}

}
