package underflowers.stackunderflow.ui.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = "/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // check if it's a public resource
        // i.e. stylesheets, login, register, etc...
        if (isPublicResource(request.getRequestURI())) {
            chain.doFilter(req, resp);
            return;
        }

        // if no user was found in the session, we need to redirect to the login page
        if (request.getSession().getAttribute("authUser") == null) {
            response.sendRedirect("login");
            return;
        }

        chain.doFilter(req, resp);
    }

    boolean isPublicResource(String uri) {
        return uri.startsWith("/assets")
                || uri.startsWith("/login")
                || uri.startsWith("/register")
                || uri.startsWith("/home")
                || uri.startsWith("/questions")
                || uri.equals("/") // Homepage
                || uri.equals("/arquillian-managed/ArquillianServletRunner"); // Arquillian servlet used for integration tests

    }
}
