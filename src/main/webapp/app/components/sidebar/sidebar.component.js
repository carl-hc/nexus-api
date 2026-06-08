angular.module('nexus')

.component('nxSidebar', {
    transclude: true,
    bindings: {
        title: '@'
    },
    templateUrl: 'components/sidebar/sidebar.html'
});
