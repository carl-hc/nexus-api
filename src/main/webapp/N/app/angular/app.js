angular.module('nexus', ['ngRoute', 'nexus.controllers'])


.config(function ($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'pages/page_home.html',
			controller: 'HomeController'
		})
		.when('/projects', {
			templateUrl: 'pages/page_projects.html',
			controller: 'ProjectsController'
		})/*
		.when('/projects/:id', {
            templateUrl: 'pages/page_project.html',
        	controller: 'ProjectController'
        })
		.when('/pipelines', {
        	templateUrl: 'pages/page_pipelines.html',
        	controller: 'ControllerPipelines'
        })
        .when('/pipelines/:id', {
                templateUrl: 'pages/page_pipeline.html',
                controller: 'ControllerPipeline'
        })
		.when('/builds', {
			templateUrl: 'pages/page_builds.html',
			controller: 'ControllerBuilds'
		})
		.when('/build/:id', {
			templateUrl: 'pages/page_build.html',
			controller: 'ControllerBuild'
		})*/
		.otherwise({
			redirectTo: '/'
		});
});
