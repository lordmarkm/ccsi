define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantController', ['$scope', '$stateParams', '$state', 'TenantService',
    function($scope, $stateParams, $state, TenantService) {

    console.debug('Loading tenant. id=' + $stateParams.tenantId);
    $scope.tenant = TenantService.get({tenantId: $stateParams.tenantId});

  }]);
});
