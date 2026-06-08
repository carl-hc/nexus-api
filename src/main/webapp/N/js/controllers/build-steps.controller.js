angular.module('nexusApp')
.controller('BuildStepsController', function (
    $scope,
    $routeParams,
    BuildStepService
) {

    $scope.buildId = $routeParams.buildId;

    $scope.steps = [];

    function loadSteps() {
        BuildStepService.findAllByBuildId($scope.buildId)
            .then(function (response) {
                $scope.steps = response.data;
            });
    }

    loadSteps();
});