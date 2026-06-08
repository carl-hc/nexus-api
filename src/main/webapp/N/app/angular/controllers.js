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
            .then(function (projects) {
                $scope.data.projects = projects.data;

                $scope.data.projects.forEach(function (project) {
                    PipelineService.findAllByProjectId(project.id)
                        .then(function (pipelines) {
                            project.pipelines = pipelines.data;
                        });
                });
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

});
