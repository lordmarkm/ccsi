define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantCreateController', ['$scope', '$state', '$stateParams', 'toaster', 'TenantService',
    function($scope, $state, $stateParams, toaster, TenantService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.tenant = {};
    $scope.saveTenant = function () {
      TenantService.save($scope.tenant, function (saved) {
        $scope.tenant = saved;
        $state.go('home', {tenantIndex: '-1'});
      }, function (fail) {
        toaster.pop('error', 'Create tenant failed', fail.data);
      });
    };

  }]);
});
