package br.com.fiap.grupo30.fastfood.infrastructure.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AdminAuthorizationAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static String ADMIN_ROLE = "admin-group";
    private static String BEARER_TYPE = "Bearer";
    private static Integer JWT_PARTS = 3;

    @Before("@annotation(AdminRequired)")
    public void checkAdminRole() throws Exception {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        HttpServletResponse response = attrs.getResponse();

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TYPE)) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String jwtToken = authorizationHeader.substring(7);

        String[] tokenParts = jwtToken.split("\\.");

        if (tokenParts.length != JWT_PARTS) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token structure");
            return;
        }

        String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
        JsonNode jsonNode = objectMapper.readTree(payload);

        JsonNode groupsNode = jsonNode.get("cognito:groups");
        if (groupsNode != null && groupsNode.isArray()) {
            List<String> groups = objectMapper.convertValue(groupsNode, List.class);
            if (!groups.contains(ADMIN_ROLE)) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED, "User does not have admin role");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No cognito:groups found");
            return;
        }
    }
}
