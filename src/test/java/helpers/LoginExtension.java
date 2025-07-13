package helpers;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import steps.ApiAccountSteps;
import steps.ApiBookStoreSteps;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        ApiAccountSteps apiStep = new ApiAccountSteps();
        apiStep.authorize();
        apiStep.checkAndUpdateToken();
    }
}

