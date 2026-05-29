angular.module('nexusApp')
.controller('ProjectParamsController', function (
    $scope,
    $routeParams,
    ProjectParamService
) {

    $scope.projectId = $routeParams.projectId;

    $scope.params = [];

    $scope.param = {
        projectId: $scope.projectId,
        name: '',
        value: ''
    };

    function loadParams() {
        ProjectParamService
            .findAllByProjectId($scope.projectId)
            .then(function (response) {
                $scope.params = response.data;
            });
    }

    $scope.createParam = function () {

        ProjectParamService
            .create($scope.param)
            .then(function () {

                $scope.param = {
                    projectId: $scope.projectId,
                    name: '',
                    value: ''
                };

                loadParams();
            });
    };

    $scope.deleteParam = function (id) {

        ProjectParamService
            .delete(id)
            .then(function () {
                loadParams();
            });
    };

    loadParams();

});