package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		try {
		    Algorithm algoritmo = Algorithm.HMAC256(secret);
		    String token = JWT.create()
		        .withIssuer("API Voll.med")
		        .withSubject(usuario.getLogin())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(dataExpiracao())
		        .sign(algoritmo);
		    return token;
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
	}

	private Instant dataExpiracao() {
		Instant data = LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
		return data;
	}
	
	public String getSubject(String tokenJWT) {
		
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
		    return JWT.require(algoritmo)
		        // specify an specific claim validations
		        .withIssuer("API Voll.med")
		        // reusable verifier instance
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		        
		} catch (JWTVerificationException exception){
		    // Invalid signature/claims
			throw new RuntimeException("Token JWT inválido ou expirado");
		}
	}
	
	

}
