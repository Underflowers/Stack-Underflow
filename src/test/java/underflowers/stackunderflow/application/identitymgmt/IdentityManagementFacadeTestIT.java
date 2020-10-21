package underflowers.stackunderflow.application.identitymgmt;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import underflowers.stackunderflow.application.ServiceRegistry;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.application.question.ProposeQuestionCommand;
import underflowers.stackunderflow.application.question.QuestionFacade;
import underflowers.stackunderflow.domain.user.UserId;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Arquillian.class)
public class IdentityManagementFacadeTestIT {

    private final static String WARNAME = "arquillian-managed.war";

    @Inject
    ServiceRegistry serviceRegistry;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        // Don't forget to fetch all package that will be used in our tests
        // Best way if we have lot of dependencies -> ShrinkWrap all pom.xml dependencies
        WebArchive archive = ShrinkWrap.create(WebArchive.class, WARNAME)
                .addPackages(true, "underflowers.stackunderflow")
                .addPackages(true, "org.mindrot")
                .addPackages(true, "org.opentest4j");
        return archive;
    }

    @Test
    public void canRegisterUserAndAuthenticateIt() {
        String firstname = "john";
        String lastname = "doe";
        String email = firstname + lastname + "@" + System.currentTimeMillis() + ".com";
        String password = "john";

        IdentityManagementFacade identityManagementFacade = serviceRegistry.getIdentityManagementFacade();
        RegistrationCommand registrationCommand = RegistrationCommand.builder()
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .clearPassword(password)
                .build();

        AuthenticateCommand authenticateCommand = AuthenticateCommand.builder()
                .email(email)
                .clearPassword(password)
                .build();

        try {
            // Register user
            identityManagementFacade.register(registrationCommand);
            // Authenticate user
            assertDoesNotThrow(() -> identityManagementFacade.authenticate(authenticateCommand));

            AuthenticatedUserDTO userDTO = identityManagementFacade.authenticate(authenticateCommand);

            // Authenticate user must be same as the created one
            assertEquals(firstname, userDTO.getFirstname());
            assertEquals(lastname, userDTO.getLastname());
            assertEquals(email, userDTO.getEmail());

        } catch (RegistrationFailedException | AuthenticationFailedException e) {
            e.printStackTrace();
        }
    }

}
