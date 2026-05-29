angular.module('nexusApp')
.controller('PipelinesController', function (
    $scope,
    $routeParams,
    PipelineService
) {

    $scope.projectId = $routeParams.projectId;

    $scope.pipelines = [];

    $scope.pipeline = {
        projectId: $scope.projectId,
        name: ''
    };

    function loadPipelines() {
        PipelineService.findAllByProjectId($scope.projectId)
            .then(function (response) {
                $scope.pipelines = response.data;
            });
    }

    $scope.createPipeline = function () {
        PipelineService.create($scope.pipeline)
            .then(function () {
                $scope.pipeline.name = '';
                loadPipelines();
            });
    };

    $scope.deletePipeline = function (id) {
        PipelineService.delete(id)
            .then(function () {
                loadPipelines();
            });
    };

    $scope.executePipeline = function (id) {
        PipelineService.execute(id)
            .then(function () {
                alert('Pipeline ejecutado');
            });
    };

    loadPipelines();

});