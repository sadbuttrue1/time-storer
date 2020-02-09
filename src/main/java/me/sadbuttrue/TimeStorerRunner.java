package me.sadbuttrue;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@Component
@RequiredArgsConstructor
public class TimeStorerRunner implements CommandLineRunner, ExitCodeGenerator {
    private final TimeStorerCommand command;

    private final IFactory factory; // auto-configured to inject PicocliSpringFactory

    private int exitCode;

    @Override
    public void run(String... args) {
        exitCode = new CommandLine(command, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
