angular.module('nexusApp')
.controller('DashboardController', function ($scope, SystemService) {

    $scope.health = 'UNKNOWN';

    SystemService.health().then(function (response) {
        $scope.health = response.data;
    });
});