angular.module('nexusApp')

.factory('BuildService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/builds/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/builds');
        },

        findAllByPipelineId: function (pipelineId) {
            return $http.get(URL_BASE + '/pipelines/' + pipelineId + '/builds');
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/builds/' + id);
        }
    };

})

.factory('BuildStepService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/buildSteps/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/buildSteps');
        },

        findAllByBuildId: function (buildId) {
            return $http.get(URL_BASE + '/builds/' + buildId + '/buildSteps');
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/buildSteps/' + id);
        }
    };

})

.factory('PipelineService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/pipelines/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/pipelines');
        },

        findAllByProjectId: function (projectId) {
            return $http.get(URL_BASE + '/projects/' + projectId + '/pipelines');
        },

        create: function (pipeline) {
            return $http.post(URL_BASE + '/pipelines', pipeline);
        },

        update: function (id, pipeline) {
            return $http.put(URL_BASE + '/pipelines/' + id, pipeline);
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/pipelines/' + id);
        },

        execute: function (id) {
            return $http.post(URL_BASE + '/pipelines/' + id + '/execute');
        }
    };

})

.factory('PipelineStepService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/pipelineSteps/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/pipelineSteps');
        },

        findAllByPipelineId: function (pipelineId) {
            return $http.get(URL_BASE + '/pipelines/' + pipelineId + '/pipelineSteps');
        },

        create: function (pipelineStep) {
            return $http.post(URL_BASE + '/pipelineSteps', pipelineStep);
        },

        update: function (id, pipelineStep) {
            return $http.put(URL_BASE + '/pipelineSteps/' + id, pipelineStep);
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/pipelineSteps/' + id);
        }
    };

})

.factory('ProjectService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/projects/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/projects');
        },

        create: function (project) {
            return $http.post(URL_BASE + '/projects', project);
        },

        update: function (id, project) {
            return $http.put(URL_BASE + '/projects/' + id, project);
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/projects/' + id);
        }
    };

})

.factory('ProjectParamService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        findById: function (id) {
            return $http.get(URL_BASE + '/projectParams/' + id);
        },

        findAll: function () {
            return $http.get(URL_BASE + '/projectParams');
        },

        findAllByProjectId: function (projectId) {
            return $http.get(URL_BASE + '/projects/' + projectId + '/projectParams');
        },

        create: function (projectParam) {
            return $http.post(URL_BASE + '/projectParams', projectParam);
        },

        update: function (id, projectParam) {
            return $http.put(URL_BASE + '/projectParams/' + id, projectParam);
        },

        delete: function (id) {
            return $http.delete(URL_BASE + '/projectParams/' + id);
        }
    };

})

.factory('SystemService', function ($http) {

    var URL_BASE = '/api/v1';

    return {
        health: function () {
            return $http.get(URL_BASE + '/system/health');
        }
    };

});