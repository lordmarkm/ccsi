define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('SidebarController', ['$scope', '$state', '$stateParams', '$rootScope', 'tenants', 'record',
    function($scope, $state, $stateParams, $rootScope, tenants, record) {

    $scope.tenants = tenants;
    $scope.record = record;

    //Handle tenants
    $scope.loadTenant = function (tenant) {
      $scope.tenant = tenant;
      $scope.tenantIndex = findIndex(tenant);
      $rootScope.$broadcast('loadTenant', {
        tenant: tenant,
        tenantIndex: $scope.tenantIndex
      });
    };
    function findIndex(tenant) {
      var i = tenants.length;
      while (i--) {
        if (tenants[i].id === tenant.id) {
          return i;
        }
      }
      console.debug('Could not find match. returning 0');
      return 0;
    }
    if ($stateParams.tenantId) {
      //This case covers tenant-specific pages where tenant should have been loaded already
      $scope.tenantIndex = findIndex({id: parseInt($stateParams.tenantId)});
      $scope.tenant = $scope.tenants[$scope.tenantIndex];
      $rootScope.$broadcast('setTenant', $scope.tenant);
    } else if (tenants.length) {
      //This covers home page
      var index = $stateParams.tenantIndex && $stateParams.tenantIndex < tenants.length ? $stateParams.tenantIndex : 0;
      if ($stateParams.tenantIndex === '-1') index = tenants.length - 1;
      $scope.loadTenant(tenants[index]);
    }

    //Handle customer record (if any)
  }]);
});
