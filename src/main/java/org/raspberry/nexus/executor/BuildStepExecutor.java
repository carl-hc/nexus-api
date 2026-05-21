package org.raspberry.nexus.executor;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.entity.Build;
import org.raspberry.nexus.entity.BuildStatus;
import org.raspberry.nexus.entity.BuildStep;
import org.raspberry.nexus.entity.PipelineStep;
import org.raspberry.nexus.exception.BadRequestException;
import org.raspberry.nexus.repository.BuildStepRepository;
import org.raspberry.nexus.utils.CommandUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BuildStepExecutor {

    private final BuildStepRepository buildStepRepository;

    private final ProcessExecutor processExecutor;

    public void execute(PipelineStep pipelineStep, Build build, File workspace, Map<String, String> params) {
        BuildStep buildStep = new BuildStep();
        buildStep.setPipelineStep(pipelineStep);
        buildStep.setBuild(build);

        try {
            buildStep = iniProcess(buildStep, BuildStatus.RUNNING);

            exeProcess(pipelineStep, buildStep, workspace, params);

            buildStep = endProcess(buildStep, BuildStatus.SUCCESS);
        } catch (Exception ex) {
            buildStep = endProcess(buildStep, BuildStatus.FAILED);

            throw ex;
        }
    }

    private BuildStep iniProcess(BuildStep buildStep, BuildStatus status) {
        buildStep.setStatus(status);
        buildStep.setIniProcess(LocalDateTime.now());

        return buildStepRepository.save(buildStep);
    }

    private void exeProcess(PipelineStep pipelineStep, BuildStep buildStep, File workspace, Map<String, String> params) {
        List<String> command = CommandUtils.parseCommand(pipelineStep, params);

        Consumer<String> outHandler = System.out::println;
        Consumer<String> errHandler = System.err::println;

        int exitCode = processExecutor.execute(workspace, command, outHandler, errHandler);

        if (exitCode != 0) {
            throw new BadRequestException("BuildStep with id '%s' failed", buildStep.getId());
        }
    }

    private BuildStep endProcess(BuildStep buildStep, BuildStatus status) {
        buildStep.setStatus(status);
        buildStep.setEndProcess(LocalDateTime.now());

        return buildStepRepository.save(buildStep);
    }

}