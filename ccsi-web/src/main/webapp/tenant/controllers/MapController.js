define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('MapController', ['$scope', '$state', 'OrgService',
    function($scope, $state, OrgService) {

    $scope.orgs = OrgService.query();
    $scope.organization = 
    $scope.name = 'MapController';
    $scope.whatever = {name: 'helloworld'};

  }]);
});
