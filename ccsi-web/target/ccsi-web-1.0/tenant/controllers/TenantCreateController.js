define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantCreateController', ['$scope', '$state', 'TenantService',
    function($scope, $state, TenantService) {

    $scope.tenant = {};
    $scope.saveTenant = function () {
      TenantService.save($scope.tenant, function (saved) {
        $scope.tenant = saved;
        $state.go('home');
      });
    };

  }]);
});
