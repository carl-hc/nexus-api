angular.module('nexus')

.component('nxBrand', {
    templateUrl: 'components/brand/brand.html',
    controller: BrandController,
    bindings: {
        icon: '@',
        color: '@'
    }
});

function BrandController() {

    var $ctrl = this;

    $ctrl.$onInit = function () {

        $ctrl.color = $ctrl.color || 'default';

    };

}