define([], function() {
  'use strict';
  return {
    tenants: ['TenantService', function (TenantService) {
      return TenantService.query().$promise;
    }],
    record: ['$stateParams', 'RecordService', function ($stateParams, RecordService) {
      console.debug('Stateparams=' + JSON.stringify($stateParams));
      console.debug('Setting record, if any. tenantId=' + $stateParams.tenantId + ', tenantRecordId=' + $stateParams.tenantRecordId);
      if ($stateParams.tenantId && $stateParams.tenantRecordId) {
        return RecordService.get({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}, function (g) {
          console.debug('wat=' + JSON.stringify(g));
        }).$promise;
      }
    }]
  };
});