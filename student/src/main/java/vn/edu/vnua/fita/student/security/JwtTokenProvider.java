package vn.edu.vnua.fita.student.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.domain.exception.JwtTokenInvalid;
import vn.edu.vnua.fita.student.model.authentication.UserDetailsImpl;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.AdminRefresher;
import vn.edu.vnua.fita.student.model.entity.StudentRefresher;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRefresherRepository;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final StudentRefresherRepository studentRefresherRepository;

    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private static final String AUTHORITIES_KEY = "XAUTHOR";

    @PostConstruct
    public void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        log.info("jwt secret: {}", jwtSecret);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenWithAuthorities(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public StudentRefresher createStudentRefreshToken(String username) {
        return StudentRefresher.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .student(Student.builder().id(username).build())
                .build();
    }

    public AdminRefresher createAdminRefreshToken(String username) {
        return AdminRefresher.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .admin(Admin.builder().id(username).build())
                .build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public UsernamePasswordAuthenticationToken getUserInfoFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.get(AUTHORITIES_KEY) != null && claims.get(AUTHORITIES_KEY) instanceof String authoritiesStr && !Strings.isBlank(authoritiesStr)) {
            Collection authorities = Arrays.stream(authoritiesStr.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);

        } else {
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", new ArrayList<>());
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        throw new JwtTokenInvalid(ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.TOKEN_INVALID));
    }
}
