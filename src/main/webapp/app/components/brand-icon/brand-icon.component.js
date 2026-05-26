angular.module('nexus')

.component('nxBrandIcon', {
    templateUrl: 'components/brand-icon/brand-icon.html',
    controller: BrandIconController,
    bindings: {
        icon: '@',
        color: '@'
    }
});

function BrandIconController() {

    var $ctrl = this;

    $ctrl.$onInit = function () {

        $ctrl.color = $ctrl.color ? $ctrl.color : 'default';

    };

}