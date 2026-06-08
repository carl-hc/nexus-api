angular.module('nexus')

.component('nxBrand', {
    templateUrl: 'components/brand/brand.html',
    controller: BrandController,
    bindings: {
        icon: '@',
        text: '@',
        color: '@'
    }
});

function BrandController() {

    var $ctrl = this;

}