define([], function() {
  'use strict';
  return {
    tenant: ['$stateParams', 'TenantService', function ($stateParams, TenantService) {
      return TenantService.get({tenantId: $stateParams.tenantId}).$promise;
    }]
  };
});