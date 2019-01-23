package app;

import com.smartthings.sdk.smartapp.core.SmartApp;
import com.smartthings.sdk.smartapp.core.SmartAppDefinition;
import com.smartthings.sdk.smartapp.core.models.ExecutionRequest;
import com.smartthings.sdk.smartapp.core.models.ExecutionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {
    private final SmartApp smartApp;

    public AppController(SmartAppDefinition smartAppDefinition) {
        smartApp = SmartApp.of(smartAppDefinition);
    }

    @GetMapping("/")
    public String home() {
        return "This app only functions as a SmartThings Automation webhook endpoint app";
    }

    @PostMapping("/smartapp")
    public ExecutionResponse handle(@RequestBody ExecutionRequest executionRequest) {
        return smartApp.execute(executionRequest);
    }
}
