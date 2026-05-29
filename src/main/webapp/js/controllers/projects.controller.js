angular.module('nexusApp')
.controller('ProjectsController', function ($scope, ProjectService) {

    $scope.projects = [];

    $scope.project = {
        name: ''
    };

    function loadProjects() {
        ProjectService.findAll().then(function (response) {
            $scope.projects = response.data;
        });
    }

    $scope.createProject = function () {
        ProjectService.create($scope.project)
            .then(function () {
                $scope.project = { name: '' };
                loadProjects();
            });
    };

    $scope.deleteProject = function (id) {
        ProjectService.delete(id)
            .then(function () {
                loadProjects();
            });
    };

    loadProjects();
});