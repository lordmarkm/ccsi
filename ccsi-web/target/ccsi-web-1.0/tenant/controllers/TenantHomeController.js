define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantCreateController', ['$scope', '$state', 'TenantService',
    function($scope, $state, TenantService) {

    $scope.tenants = TenantService.query();

  }]);
});
