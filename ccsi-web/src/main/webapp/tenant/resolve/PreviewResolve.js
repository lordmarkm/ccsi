define([], function() {
  'use strict';
  return {
    templates: ['$stateParams', 'TemplateService', function ($stateParams, TemplateService) {
      return TemplateService.query({tenantId: $stateParams.tenantId}).$promise;
    }],
    previews: ['$stateParams', 'PreviewService', function ($stateParams, PreviewService) {
      return PreviewService.query({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}).$promise;
    }],
    tenantRecord: ['$stateParams', 'RecordService', function ($stateParams, RecordService) {
      return RecordService.get({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}).$promise;
    }]
  };
});