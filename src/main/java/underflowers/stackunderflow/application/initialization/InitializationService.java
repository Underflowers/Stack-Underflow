package underflowers.stackunderflow.application.initialization;

import io.underflowers.underification.Configuration;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class InitializationService {
    @PostConstruct
    public void init() {
        Configuration.getDefaultApiClient().setApiKey(System.getenv("UNDERIFICATION_AUTH_TOKEN"));
        Configuration.getDefaultApiClient().setBasePath(System.getenv("UNDERIFICATION_URL"));
    }
}
