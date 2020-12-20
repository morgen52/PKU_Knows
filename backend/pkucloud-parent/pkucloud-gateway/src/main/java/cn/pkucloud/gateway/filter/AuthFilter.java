package cn.pkucloud.gateway.filter;

import cn.pkucloud.common.Result;
import cn.pkucloud.gateway.entity.JwtResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static cn.pkucloud.common.ResultCode.AUTHORIZATION_REQUIRED;

@Data
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String jwsStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (!StringUtils.isBlank(jwsStr)) {
            JwtResult jwtResult = verifyJws(jwsStr);
            if (jwtResult.isValid()) {
                exchange.getRequest().mutate().headers(httpHeaders -> {
                    httpHeaders.add("iss", jwtResult.getIssuer());
                    httpHeaders.add("id", jwtResult.getSubject());
                    httpHeaders.add("role", jwtResult.getRole());
                    httpHeaders.add("mod", String.valueOf(jwtResult.getMod()));
                });
                System.out.println("jwtResult = " + jwtResult);
                return chain.filter(exchange);
            }
        }
        return errorResponse(exchange.getResponse(), new Result<>(AUTHORIZATION_REQUIRED, "authorization required"));
    }

    private Mono<Void> errorResponse(ServerHttpResponse response, Result<?> result) {
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 10000;
    }

    private JwtResult verifyJws(String jwsStr) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwsStr)
                    .getBody();
            String issuer = claims.getIssuer();
            String subject = claims.getSubject();
            String role = claims.get("role", String.class);
            int mod = (int) claims.get("mod");
            return new JwtResult(true, "ok", issuer, subject, role, mod);
        } catch (ExpiredJwtException e) {
            return new JwtResult(false, "token expired");
        } catch (JwtException e) {
            return new JwtResult(false, "token invalid");
        }
    }
}
