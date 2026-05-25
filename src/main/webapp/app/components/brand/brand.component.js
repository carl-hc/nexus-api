angular.module('nexus')

.component('nxBrand', {
    templateUrl: 'components/brand/brand.html',
    controller: BrandController,
    bindings: {
        text: '@',
        icon: '@',
        color: '@'
    }
});

function BrandController() {

    var vm = this;

    vm.$onInit = function () {

        vm.color = vm.color || 'default';

        vm.initial = '';

        if (vm.text) {
            vm.initial = vm.text.trim().charAt(0).toUpperCase();
        }

    };

}