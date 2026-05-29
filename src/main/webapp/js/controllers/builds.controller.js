angular.module('nexusApp')
.controller('BuildsController', function (
    $scope,
    $routeParams,
    BuildService
) {

    $scope.pipelineId = $routeParams.pipelineId;

    $scope.builds = [];

    function loadBuilds() {
        BuildService.findAllByPipelineId($scope.pipelineId)
            .then(function (response) {
                $scope.builds = response.data;
            });
    }

    $scope.deleteBuild = function (id) {
        BuildService.delete(id)
            .then(function () {
                loadBuilds();
            });
    };

    loadBuilds();
});