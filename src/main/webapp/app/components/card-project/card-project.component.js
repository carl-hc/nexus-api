angular.module('nexus')

.component('nxCardProject', {
    templateUrl: 'components/card-project/card-project.html',
    controller: CardProjectController,
    bindings: {
        project: '<',
    }
});

function CardProjectController() {

    var $ctrl = this;

}