package app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.smartthings.sdk.smartapp.core.SmartApp;
import com.smartthings.sdk.smartapp.core.SmartAppDefinition;
import com.smartthings.sdk.smartapp.core.models.AppLifecycle;
import com.smartthings.sdk.smartapp.core.models.ExecutionRequest;
import com.smartthings.sdk.smartapp.core.models.ExecutionResponse;
import com.smartthings.sdk.smartapp.spring.HttpVerificationService;


@RestController
public class AppController {
    private final SmartApp smartApp;
    private final HttpVerificationService httpVerificationService;

    public AppController(SmartAppDefinition smartAppDefinition, HttpVerificationService httpVerificationService) {
        smartApp = SmartApp.of(smartAppDefinition);
        this.httpVerificationService = httpVerificationService;
    }

    @GetMapping("/")
    public String home() {
        return "This app only functions as a SmartThings Automation webhook endpoint app";
    }

    @PostMapping("/smartapp")
    public ExecutionResponse handle(@RequestBody ExecutionRequest executionRequest, HttpServletRequest request) {
        if (executionRequest.getLifecycle() != AppLifecycle.PING) {
            if (!httpVerificationService.verify(request)) {
                throw new UnauthorizedException("unable to verify request");
            }
        }
        return smartApp.execute(executionRequest);
    }
}
