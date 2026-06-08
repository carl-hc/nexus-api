angular.module('nexus.controllers')

.controller('SidebarController', [
    '$scope',
    '$location',
    '$q',
    'Api',
    function (
        $scope,
        $location,
        $q,
        Api
    ) {

        $scope.stats = {
            projects: 0,
            pipelines: 0,
            builds: 0,
            health: false
        };

        $scope.isActive = isActive;

        activate();

        function activate() {

            $q.all({

                projects: Api.project.findAll(),
                pipelines: Api.pipeline.findAll(),
                builds: Api.build.findAll(),
                health: Api.system.health()

            })

            .then(function (result) {

                $scope.stats.projects =
                    result.projects.data.length;

                $scope.stats.pipelines =
                    result.pipelines.data.length;

                $scope.stats.builds =
                    result.builds.data.length;

                $scope.stats.health =
                    result.health.data;

            });

        }

        function isActive(path) {

            return $location.path()
                .indexOf(path) === 0;

        }

    }
]);