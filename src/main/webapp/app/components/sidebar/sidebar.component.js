angular.module('nexus')

.component('nxSidebar', {
    templateUrl: 'components/sidebar/sidebar.html',
    controller: SidebarController,
    bindings: {
        active: '@'
    }
});

function SidebarController() {

    var $ctrl = this;

    $ctrl.$onInit = function () {

        $ctrl.active = $ctrl.active ? $ctrl.active : 'home';

    };

}