package underflowers.stackunderflow.ui.web.profile;

import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.IdentityManagementFacade;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.profile.EditUserCommand;
import underflowers.stackunderflow.application.identitymgmt.profile.EditUserFailedException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditProfileCommandServlet", urlPatterns = "/edit-profile.do")
public class EditProfileCommandServlet extends HttpServlet {

    @Inject
    ServiceRegistry serviceRegistry;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();

        // Check that new password and new password repeat must be same
        if(!request.getParameter("newPassword").equals(request.getParameter("newPasswordRepeat"))){
            request.getSession().setAttribute("errors", List.of("New password and new password repeat must be the same"));
            response.sendRedirect("edit-profile");
        }

        AuthenticatedUserDTO currentUser = (AuthenticatedUserDTO) request.getSession().getAttribute("authUser");
        EditUserCommand editUserCommand = EditUserCommand.builder()
                .newFirstname(request.getParameter("firstname"))
                .newLastname(request.getParameter("lastname"))
                .newEmail(request.getParameter("email"))
                .oldEmail(currentUser.getEmail())
                .clearOldPassword(request.getParameter("oldPassword"))
                .clearNewPassword(request.getParameter("newPassword"))
                .build();
        try {
            // Edit the user and fetch the UpdatedUser to store it into the session as the new authUser
            AuthenticatedUserDTO updatedUser = identityManagementFacade.editUser(editUserCommand);
            request.getSession().setAttribute("authUser", updatedUser);
            request.getSession().setAttribute("updated", true);
            response.sendRedirect("/profile");
        } catch (EditUserFailedException e) {
            request.getSession().setAttribute("errors", List.of(e.getMessage()));
            response.sendRedirect("/edit-profile");
        }
    }
}
