define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('MapController', ['$rootScope', '$scope', '$state', '$modal', 'OrgService',
    function($rootScope, $scope, $state, $modal, OrgService) {

    $scope.orgs = OrgService.query();
    $scope.organization = 
    $scope.name = 'MapController';
    $scope.whatever = {name: 'helloworld'};
    
    $rootScope.$on('eventsUpdate', function (evt, events) {
      console.debug('got some events yo');
      $scope.events = events;
    });
  }]);
});
