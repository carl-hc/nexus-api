angular.module('nexus')

.component('nxSidebarItem', {
    bindings: {
        href: '@',
        icon: '@',
        label: '@',
        active: '<'
    },
    templateUrl: 'components/sidebar-item/sidebar-item.html'
});
