package org.rifasya.main.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // --- PASO 1: EXTRAER EL TOKEN DEL HEADER ---
        final String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", no hacemos nada y continuamos.
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraemos el token JWT (quitando el prefijo "Bearer ").
        final String jwt = authHeader.substring(7);

        // --- PASO 2: VALIDAR EL TOKEN Y ESTABLECER LA AUTENTICACIÓN ---
        try {
            final String username = jwtUtil.extractUsername(jwt);

            // Si tenemos un username y el usuario aún no está autenticado en esta petición.
            if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Si el token es válido (firma y no expirado)
                if (jwtUtil.validateToken(jwt)) {
                    // Creamos el objeto de autenticación para Spring Security.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Las credenciales son nulas porque ya validamos el token
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("No se pudo establecer la autenticación del usuario: {} \n" + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

