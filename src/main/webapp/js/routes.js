angular.module('nexusApp')
.config(function ($routeProvider) {

    $routeProvider

        .when('/', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardController'
        })

        .when('/projects', {
            templateUrl: 'views/projects.html',
            controller: 'ProjectsController'
        })

        .when('/projects/:projectId/params', {
            templateUrl: 'views/project-params.html',
            controller: 'ProjectParamsController'
        })

        .when('/projects/:projectId/pipelines', {
            templateUrl: 'views/pipelines.html',
            controller: 'PipelinesController'
        })

        .when('/pipelines/:pipelineId/steps', {
            templateUrl: 'views/pipeline-steps.html',
            controller: 'PipelineStepsController'
        })

        .when('/pipelines/:pipelineId/builds', {
            templateUrl: 'views/builds.html',
            controller: 'BuildsController'
        })

        .when('/builds/:buildId/steps', {
            templateUrl: 'views/build-steps.html',
            controller: 'BuildStepsController'
        })

        .otherwise({
            redirectTo: '/'
        });
});