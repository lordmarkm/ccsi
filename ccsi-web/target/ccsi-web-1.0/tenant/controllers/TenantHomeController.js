define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantHomeController', ['$scope', '$state', 'TenantService',
    function($scope, $state, TenantService) {

    $scope.tenants = TenantService.query(function (tenants) {
      if (tenants.length) {
        $scope.tenant = tenants[0];
      }
    });

  }]);
});
