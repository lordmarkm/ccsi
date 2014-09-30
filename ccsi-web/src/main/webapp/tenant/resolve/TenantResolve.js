define([], function() {
  'use strict';
  return {
    tenants: ['TenantService', function (TenantService) {
      return TenantService.query().$promise;
    }]
  };
});