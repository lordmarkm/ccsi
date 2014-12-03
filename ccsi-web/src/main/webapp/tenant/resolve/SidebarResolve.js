define([], function() {
  'use strict';
  return {
    tenants: ['TenantService', function (TenantService) {
      return TenantService.query().$promise;
    }],
    record: ['$stateParams', 'RecordService', function ($stateParams, RecordService) {
      if ($stateParams.tenantId && $stateParams.tenantRecordId) {
        return RecordService.get({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}, function (g) {
          console.debug('wat=' + JSON.stringify(g));
        }).$promise;
      }
    }]
  };
});