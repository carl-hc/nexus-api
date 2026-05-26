angular.module('nexus')

.component('nxBrandText', {
    templateUrl: 'components/brand-text/brand-text.html',
    controller: BrandTextController,
    bindings: {
        text: '@',
        color: '@'
    }
});

function BrandTextController() {

    var $ctrl = this;

    $ctrl.$onInit = function () {

        $ctrl.color = $ctrl.color ? $ctrl.color : 'default';
        $ctrl.text = $ctrl.text ? $ctrl.text.charAt(0).toUpperCase() : '';

    };

}