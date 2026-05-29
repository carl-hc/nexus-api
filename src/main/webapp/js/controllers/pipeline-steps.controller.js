angular.module('nexusApp')
.controller('PipelineStepsController', function (
    $scope,
    $routeParams,
    PipelineStepService
) {

    $scope.pipelineId = $routeParams.pipelineId;

    $scope.steps = [];

    $scope.step = {
        pipelineId: $scope.pipelineId,
        name: '',
        order: 1,
        command: ''
    };

    function loadSteps() {
        PipelineStepService.findAllByPipelineId($scope.pipelineId)
            .then(function (response) {
                $scope.steps = response.data;
            });
    }

    $scope.createStep = function () {
        PipelineStepService.create($scope.step)
            .then(function () {

                $scope.step = {
                    pipelineId: $scope.pipelineId,
                    name: '',
                    order: 1,
                    command: ''
                };

                loadSteps();
            });
    };

    $scope.deleteStep = function (id) {
        PipelineStepService.delete(id)
            .then(function () {
                loadSteps();
            });
    };

    loadSteps();

});