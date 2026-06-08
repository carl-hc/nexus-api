angular.module('nexus.controllers', ['nexus.services'])


.controller('HomeController', function ($scope, BuildService, PipelineService, ProjectService, SystemService) {

    $scope.data = {
        health: false,
        projects: [],
        pipelines: [],
        builds: []
    };

    $scope.reload = reload;
    $scope.executePipeline = executePipeline;
    $scope.openProject = openProject;

    reload();

    function reload() {
        loadHealth();
        loadProjects();
        loadPipelines();
        loadBuilds();
    }

    function loadHealth() {
        SystemService.health()
            .then(function (response) {
                $scope.data.health = response.data;
            });
    }

    function loadProjects() {
        ProjectService.findAll()
            .then(function (response) {
                $scope.data.projects = response.data;
            });
    }

    function loadPipelines() {
        PipelineService.findAll()
            .then(function (response) {
                $scope.data.pipelines = response.data;
            });
    }

    function loadBuilds() {
        BuildService.findAll()
            .then(function (response) {
                $scope.data.builds = response.data;
            });
    }

    function executePipeline(id) {
        PipelineService.execute(id)
            .then(function () {
                reload();
            });
    }

    function openProject(id) {
        window.location.href = '#!/projects/' + id;
    }

})

.controller('ProjectsController', function ($scope, ProjectService) {

    $scope.data = {
        projects: []
    };

    $scope.filters = {
        search: ''
    };

    $scope.reload = reload;
    $scope.createProject = createProject;
    $scope.openProject = openProject;
    $scope.editProject = editProject;
    $scope.deleteProject = deleteProject;

    reload();

    function reload() {
        loadProjects();
    }

    function loadProjects() {
        ProjectService.findAll()
            .then(function (response) {
                $scope.data.projects = response.data;
            });
    }

    function createProject() {
        var name = prompt('Project name');

        if (!name) {
            return;
        }

        var newProject = {
            name: name
        };

        ProjectService.create(newProject)
            .then(function () {
                reload();
            });
    }

    function editProject(project) {
        var name = prompt('Project name', project.name);

        if (!name) {
            return;
        }

        var newProject = angular.copy(project);

        newProject.name = name;

        ProjectService.update(project.id, newProject)
            .then(function () {
                reload();
            });
    }

    function deleteProject(id) {
        if (!confirm('Delete project #' + id + '?')) {
            return;
        }

        ProjectService.delete(id)
            .then(function () {
                reload();
            });
    }

    function openProject(id) {
        window.location.href = '#!/projects/' + id;
    }

})

.controller('ProjectController', function ($scope, $routeParams, PipelineService, ProjectService, ProjectParamService) {

    var projectId = $routeParams.id;

    $scope.data = {
        project: {},
        pipelines: [],
        params: []
    };

    $scope.goBack = goBack;
    $scope.openPipeline = openPipeline;
    $scope.executePipeline = executePipeline;
    $scope.addParam = addParam;
    $scope.deleteParam = deleteParam;
    $scope.openCreatePipeline = openCreatePipeline;

    reload();

    function reload() {
        loadProject(projectId);
        loadPipelines(projectId);
        loadProjectParams(projectId);
    }

    function loadProject(projectId) {
        ProjectService.findById(projectId)
            .then(function (response) {
                $scope.data.project = response.data;
            });
    }

    function loadPipelines(projectId) {
        PipelineService.findAllByProjectId(projectId)
            .then(function (response) {
                $scope.data.pipelines = response.data;
            });
    }

    function loadProjectParams(projectId) {
        ProjectParamService.findAllByProjectId(projectId)
            .then(function (response) {
                $scope.data.params = response.data;
            });
    }

    function openPipeline(id) {
        window.location.href = '#!/pipelines/' + id;
    }

    function executePipeline(id) {
        PipelineService.execute(id)
            .then(function () {
                reload();
            });
    }

    function addParam() {
        var name = prompt('Name');
        var value = prompt('Value');

        if (!name || !value) return;

        var newParam = {
            projectId: projectId,
            name: name,
            value: value
        };

        ProjectParamService.create(newParam)
            .then(function () {
                reload();
            });
    }

    function deleteParam(id) {
        ProjectParamService.delete(id)
            .then(function () {
                reload();
            });
    }

    function openCreatePipeline() {
        window.location.href = '#!/projects/' + projectId + '/pipelines/new';
    }

    function goBack() {
        window.history.back();
    }

})


.controller('ControllerPipelines', function ($scope, ProjectService, PipelineService) {

    $scope.data = {
        pipelines: [],
        projects: []
    };

    $scope.filters = {
        search: '',
        projectId: ''
    };

    $scope.form = {};

    loadProjects();
    reloadPipelines();

    function loadProjects() {
        ProjectService.findAll({ }, function (data) {
            $scope.data.projects = data || [];
        });
    }

    function reloadPipelines() {
        var params = {};

        if ($scope.filters.projectId) {
            params.projectId = $scope.filters.projectId;
        }

        PipelineService.findAll(params, function (data) {
            $scope.data.pipelines = data || [];
        });
    }

    $scope.reloadPipelines = reloadPipelines;

    $scope.openCreatePipelineModal = function () {
        $scope.form = {
            projectId: null,
            name: ''
        };

        $('#pipelineModal').modal('show');
    };

    $scope.editPipeline = function (pipeline) {
        $scope.form = angular.copy(pipeline);

        $('#pipelineModal').modal('show');
    };

    $scope.savePipeline = function () {
        if ($scope.form.id) {
            PipelineService.update($scope.form.id, $scope.form, function () {
                reloadPipelines();

                $('#pipelineModal').modal('hide');
            });
        } else {
            PipelineService.create($scope.form, function () {
                reloadPipelines();

                $('#pipelineModal').modal('hide');
            });
        }
    };

    $scope.deletePipeline = function (pipelineId) {
        var pipeline = findPipeline(pipelineId);

        if (!pipeline) {
            return;
        }

        if (!confirm('Delete pipeline "' + pipeline.name + '"?')) {
            return;
        }

        PipelineService.delete(pipeline.id, function () {
            reloadPipelines();
        });
    };

    $scope.openPipeline = function (pipelineId) {
        alert('Open pipeline detail: ' + pipelineId);
        // aquí luego puedes hacer $location.path('/pipeline/' + pipelineId);
    };

    $scope.getProjectName = function (projectId) {
        for (var i = 0; i < $scope.data.projects.length; i++) {
            if ($scope.data.projects[i].id === projectId) {
                return $scope.data.projects[i].name;
            }
        }

        return 'Unknown';
    };

    function findPipeline(pipelineId) {
        for (var i = 0; i < $scope.data.pipelines.length; i++) {
            if ($scope.data.pipelines[i].id === pipelineId) {
                return $scope.data.pipelines[i];
            }
        }

        return null;
    }

})

.controller('ControllerPipeline', function ($scope, $routeParams, $location, PipelineService, PipelineStepService, BuildService, ExecutorService) {

    // =========================
    // MODEL
    // =========================

    $scope.pipeline = {};
    $scope.steps = [];
    $scope.builds = [];

    $scope.tab = 'steps';

    var pipelineId = $routeParams.id;

    // =========================
    // INIT
    // =========================

    init();

    function init() {
        loadPipeline();
        loadSteps();
        loadBuilds();
    }

    // =========================
    // PIPELINE
    // =========================

    function loadPipeline() {

        PipelineService.findById(pipelineId, function (data) {
            $scope.pipeline = data || {};
        });

    }

    // =========================
    // STEPS
    // =========================

    function loadSteps() {

        PipelineStepService.findAll({
            pipelineId: pipelineId
        }, function (data) {

            $scope.steps = (data || []).sort(function (a, b) {
                return (a.order || 0) - (b.order || 0);
            });

        });

    }

    // =========================
    // BUILDS
    // =========================

    function loadBuilds() {

        BuildService.findAll({
            pipelineId: pipelineId
        }, function (data) {

            $scope.builds = (data || []).sort(function (a, b) {
                return b.id - a.id;
            });

        });

    }

    // =========================
    // NAVIGATION
    // =========================

    $scope.openBuild = function (buildId) {
        $location.path('/build/' + buildId);
    };

    // =========================
    // PIPELINE ACTIONS
    // =========================

    $scope.runPipeline = function () {

        if (!confirm('Run pipeline "' + $scope.pipeline.name + '"?')) {
            return;
        }

        ExecutorService.enqueue({
            pipelineId: pipelineId
        }, function () {

            alert('Pipeline execution started');

            loadBuilds();

        });

    };

    $scope.editPipeline = function () {

        var name = prompt('Edit pipeline name', $scope.pipeline.name);

        if (!name) return;

        PipelineService.update($scope.pipeline.id, {
            id: $scope.pipeline.id,
            projectId: $scope.pipeline.projectId,
            name: name
        }, function () {

            loadPipeline();

        });

    };

    $scope.deletePipeline = function () {

        if (!confirm('Delete pipeline "' + $scope.pipeline.name + '"?')) {
            return;
        }

        PipelineService.delete($scope.pipeline.id, function () {

            $location.path('/projects/' + $scope.pipeline.projectId);

        });

    };

    // =========================
    // STEP ACTIONS
    // =========================

    $scope.addStep = function () {

        var name = prompt('Step name');
        if (!name) return;

        var command = prompt('Command');
        if (!command) return;

        var order = prompt('Order (0,1,2...)', $scope.steps.length);
        order = parseInt(order || 0);

        PipelineStepService.create({
            pipelineId: pipelineId,
            name: name,
            command: command,
            order: order
        }, function () {

            loadSteps();

        });

    };

    $scope.editStep = function (step) {

        var name = prompt('Edit name', step.name);
        if (!name) return;

        var command = prompt('Edit command', step.command);
        if (!command) return;

        PipelineStepService.update(step.id, {
            id: step.id,
            pipelineId: step.pipelineId,
            name: name,
            command: command,
            order: step.order
        }, function () {

            loadSteps();

        });

    };

    $scope.deleteStep = function (stepId) {

        if (!confirm('Delete step?')) return;

        PipelineStepService.delete(stepId, function () {

            loadSteps();

        });

    };

    // =========================
    // HELPERS
    // =========================

    $scope.getStatusClass = function (status) {

        switch (status) {

            case 'SUCCESS': return 'bg-success';
            case 'FAILED': return 'bg-danger';
            case 'RUNNING': return 'bg-warning text-dark';
            default: return 'bg-secondary';

        }

    };

})

.controller('ControllerBuilds', function ($scope, BuildService, PipelineService) {

    $scope.data = {
        builds: [],
        pipelines: []
    };

    $scope.filters = {
        search: '',
        status: ''
    };

    $scope.form = {};

    loadPipelines();
    reloadBuilds();

    function loadPipelines() {
        PipelineService.findAll({ }, function (data) {
            $scope.data.pipelines = data || [];
        });
    }

    function reloadBuilds() {
        var params = {};

        if ($scope.filters.status) {
            params.status = $scope.filters.status;
        }

        BuildService.findAll(params, function (data) {
            $scope.data.builds = data || [];
        });
    }

    $scope.reloadBuilds = reloadBuilds;

    $scope.openCreateBuildModal = function () {
        $scope.form = {
            pipelineId: null,
            status: 'RUNNING',
            iniProcess: null,
            endProcess: null
        };

        $('#buildModal').modal('show');
    };

    $scope.editBuild = function (build) {
        $scope.form = angular.copy(build);

        $('#buildModal').modal('show');
    };

    $scope.saveBuild = function () {
        if ($scope.form.id) {
            BuildService.update($scope.form.id, $scope.form, function () {
                reloadBuilds();

                $('#buildModal').modal('hide');
            });
        } else {
            BuildService.create($scope.form, function () {
                reloadBuilds();

                $('#buildModal').modal('hide');
            });
        }
    };

    $scope.deleteBuild = function (buildId) {
        var build = findBuild(buildId);

        if (!build) {
            return;
        }

        if (!confirm('Delete build #' + build.id + '?')) {
            return;
        }

        BuildService.delete(build.id, function () {
            reloadBuilds();
        });
    };

    $scope.openBuild = function (buildId) {
        alert('Open build detail: ' + buildId);
        // aquí luego puedes hacer:
        // $location.path('/build/' + buildId);
    };

    $scope.getPipelineName = function (pipelineId) {
        for (var i = 0; i < $scope.data.pipelines.length; i++) {
            if ($scope.data.pipelines[i].id === pipelineId) {
                return $scope.data.pipelines[i].name;
            }
        }

        return 'Unknown';
    };

    function findBuild(buildId) {
        for (var i = 0; i < $scope.data.builds.length; i++) {
            if ($scope.data.builds[i].id === buildId) {
                return $scope.data.builds[i];
            }
        }

        return null;
    }

})

.controller('ControllerBuild', function ($scope, $routeParams, $interval, BuildService, BuildStepService) {

    // =========================
    // MODEL
    // =========================

    $scope.build = {};
    $scope.steps = [];
    $scope.logs = '';

    $scope.tab = 'steps';

    var buildId = $routeParams.id;

    var refreshPromise;

    // =========================
    // INIT
    // =========================

    init();

    function init() {
        loadBuild();
        loadSteps();
        loadLogs();
        startAutoRefresh();
    }

    // =========================
    // BUILD
    // =========================

    function loadBuild() {

        BuildService.findById(buildId, function (data) {
            $scope.build = data || {};
        });

    }

    // =========================
    // STEPS
    // =========================

    function loadSteps() {

        BuildStepService.findAll({
            buildId: buildId
        }, function (data) {

            $scope.steps = (data || []).sort(function (a, b) {
                return (a.id || 0) - (b.id || 0);
            });

        });

    }

    // =========================
    // LOGS (MOCK / PLACEHOLDER)
    // =========================

    function loadLogs() {

        // No tienes endpoint de logs en backend,
        // así que dejamos placeholder listo para integrar luego

        $scope.logs =
            "Initializing build...\n" +
            "Fetching pipeline...\n" +
            "Executing steps...\n";

    }

    // =========================
    // REFRESH
    // =========================

    $scope.reloadBuild = function () {

        loadBuild();
        loadSteps();

    };

    // =========================
    // DURATION
    // =========================

    $scope.getDuration = function () {

        if (!$scope.build.iniProcess) {
            return '-';
        }

        var start = new Date($scope.build.iniProcess);
        var end = $scope.build.endProcess ? new Date($scope.build.endProcess) : new Date();

        var diff = end - start;

        if (diff < 0) return '-';

        var seconds = Math.floor(diff / 1000);
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);

        seconds = seconds % 60;
        minutes = minutes % 60;

        if (hours > 0) {
            return hours + 'h ' + minutes + 'm ' + seconds + 's';
        }

        if (minutes > 0) {
            return minutes + 'm ' + seconds + 's';
        }

        return seconds + 's';

    };

    // =========================
    // AUTO REFRESH (CI STYLE)
    // =========================

    function startAutoRefresh() {

        refreshPromise = $interval(function () {

            // solo refrescar si está RUNNING
            if ($scope.build.status === 'RUNNING') {
                loadBuild();
                loadSteps();
            }

        }, 3000);

    }

    // limpiar intervalo
    $scope.$on('$destroy', function () {

        if (refreshPromise) {
            $interval.cancel(refreshPromise);
        }

    });

})

.controller('BuildDebugController', function ($scope, $routeParams) {

    // =========================
    // MODEL
    // =========================

    $scope.buildId = $routeParams.id;
    $scope.status = 'CONNECTING';
    $scope.logs = [];

    var ws;

    // =========================
    // INIT
    // =========================

    init();

    function init() {
        connectWebSocket();
    }

    // =========================
    // WEBSOCKET
    // =========================

    function connectWebSocket() {

        // ajusta endpoint backend
        ws = new WebSocket("ws://localhost:8080/ws/build/" + $scope.buildId);

        ws.onopen = function () {

            addLog("SYSTEM", "Connected to build stream", "text-success");

        };

        ws.onmessage = function (event) {

            var msg = JSON.parse(event.data);

            handleMessage(msg);

            $scope.$apply();

        };

        ws.onerror = function () {

            addLog("ERROR", "WebSocket error", "text-danger");

        };

        ws.onclose = function () {

            addLog("SYSTEM", "Connection closed", "text-warning");

        };

    }

    // =========================
    // MESSAGE HANDLER
    // =========================

    function handleMessage(msg) {

        switch (msg.type) {

            case "LOG":

                addLog(msg.step || "STEP", msg.message, "text-light");

                break;

            case "STATUS":

                $scope.status = msg.status;

                addLog("STATUS", "Build status: " + msg.status, "text-info");

                break;

            case "STEP":

                addLog("STEP", msg.step + " -> " + msg.status,
                    msg.status === "SUCCESS" ? "text-success" : "text-warning");

                break;

        }

    }

    // =========================
    // LOG HELPERS
    // =========================

    function addLog(prefix, text, className) {

        $scope.logs.push({

            time: new Date().toLocaleTimeString(),
            text: "[" + prefix + "] " + text,
            typeClass: className || "text-light"

        });

        // auto scroll (opcional si lo conectas a DOM)
    }

    // =========================
    // ACTIONS
    // =========================

    $scope.clearLogs = function () {
        $scope.logs = [];
    };

    // cleanup
    $scope.$on('$destroy', function () {
        if (ws) ws.close();
    });

});
