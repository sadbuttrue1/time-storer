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

    @Option(names = {"-p"}, description = "Print times from DB")
    private boolean print;

    @Override
    public Integer call() throws Exception {
        if (print) {
            storingService.printStoredTime();
        } else {
            storingService.startStoringAndWaitForStop();
        }
        return 0;
    }
}
