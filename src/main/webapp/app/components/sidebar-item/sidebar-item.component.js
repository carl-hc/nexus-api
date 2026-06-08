angular.module('nexus')

.component('nxSidebarItem', {
    templateUrl: 'components/sidebar-item/sidebar-item.html',
    controller: SidebarItemController,
    bindings: {
        active: '@',
        href: '@',
        icon: '@',
        text: '@'
    }
});

function SidebarItemController() {

    var $ctrl = this;

}