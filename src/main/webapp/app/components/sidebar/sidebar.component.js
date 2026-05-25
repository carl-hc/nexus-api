angular.module('nexus')

.component('nxSidebar', {
    templateUrl: 'components/sidebar/sidebar.html',
    controller: SidebarController,
    bindings: {
        active: '@'
    }
});

function SidebarController() {

    var vm = this;

}