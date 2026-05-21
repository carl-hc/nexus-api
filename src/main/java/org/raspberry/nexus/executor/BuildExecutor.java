package org.raspberry.nexus.executor;

import lombok.RequiredArgsConstructor;
import org.raspberry.nexus.entity.Build;
import org.raspberry.nexus.entity.BuildStatus;
import org.raspberry.nexus.entity.Pipeline;
import org.raspberry.nexus.entity.PipelineStep;
import org.raspberry.nexus.repository.BuildRepository;
import org.raspberry.nexus.repository.PipelineStepRepository;
import org.raspberry.nexus.utils.WorkspaceUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BuildExecutor {

    private final BuildRepository buildRepository;
    private final PipelineStepRepository pipelineStepRepository;

    private final BuildStepExecutor buildStepExecutor;

    public void execute(Pipeline pipeline, Map<String, String> params) {
        Build build = new Build();
        build.setPipeline(pipeline);

        try {
            build = iniProcess(build, BuildStatus.RUNNING);

            exeProcess(pipeline, build, params);

            build = endProcess(build, BuildStatus.SUCCESS);
        } catch (Exception ex) {
            build = endProcess(build, BuildStatus.FAILED);

            throw ex;
        }
    }

    private Build iniProcess(Build build, BuildStatus status) {
        build.setStatus(status);
        build.setIniProcess(LocalDateTime.now());

        return buildRepository.save(build);
    }

    private void exeProcess(Pipeline pipeline, Build build, Map<String, String> params) {
        File workspace = WorkspaceUtils.getWorkspace(build);

        WorkspaceUtils.createWorkspace(build);

        for (PipelineStep pipelineStep : pipelineStepRepository.findAllByPipelineId(pipeline.getId(), Sort.by("order"))) {
            buildStepExecutor.execute(pipelineStep, build, workspace, params);
        }
    }

    private Build endProcess(Build build, BuildStatus status) {
        build.setStatus(status);
        build.setEndProcess(LocalDateTime.now());

        return buildRepository.save(build);
    }


}