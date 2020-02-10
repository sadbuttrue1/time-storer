package me.sadbuttrue;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.service.StoringService;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class TimeStorerCommand implements Callable<Integer> {

    private final StoringService storingService;

    @Option(names = "-p", description = "Print times from DB")
    private boolean print;

    // Prevent "Unknown option" error when users use
    // the Spring Boot parameter 'spring.config.location' to specify
    // an alternative location for the application.properties file.
    @Option(names = "--spring.config.location", hidden = true)
    private String springConfigLocation;

    // Same for spring.application.admin.enabled that is used by spring-boot:start
    @Option(names = "--spring.application.admin.enabled", hidden = true)
    private boolean springApplicationAdminEnabled;

    // Same for spring.application.admin.jmx-name that is used by spring-boot:start
    @Option(names = "--spring.application.admin.jmx-name", hidden = true)
    private String springApplicationAdminJmxName;

    @Override
    public Integer call() throws Exception {
        if (print) {
            storingService.printStoredTime();
        } else {
            storingService.startStoring();
        }
        return 0;
    }
}
