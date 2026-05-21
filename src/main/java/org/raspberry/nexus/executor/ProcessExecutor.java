package org.raspberry.nexus.executor;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.exception.InternalServerErrorException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ProcessExecutor {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public int execute(File workspace, List<String> command, Consumer<String> outHandler, Consumer<String> errHandler) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(workspace);

        try {
            Process process = processBuilder.start();

            Future<?> outFuture = executor.submit(() -> stream(process.inputReader(), outHandler));
            Future<?> errFuture = executor.submit(() -> stream(process.errorReader(), errHandler));

            int exitCode = process.waitFor();

            outFuture.get();
            errFuture.get();

            return exitCode;
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex, "Error executing process command");
        }
    }

    private void stream(BufferedReader reader, Consumer<String> handler) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                handler.accept(line);
            }
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex, "Error streaming process output");
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

}