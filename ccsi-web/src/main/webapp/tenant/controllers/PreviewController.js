define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('PreviewController', ['$scope', '$stateParams', '$state', 'templates', 'previews', 'tenantRecord', 'RecordService', 'PreviewService', 'VariablesService',
    function($scope, $stateParams, $state, templates, previews, tenantRecord, RecordService, PreviewService, VariablesService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.previews = previews;
    $scope.tenantRecord = tenantRecord;
    $scope.templates = templates;

    $scope.saveRecord = function (record) {
      RecordService.save({tenantId: $stateParams.tenantId}, record, function (record) {
         PreviewService.query({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}, function (previews) {
           $scope.previews = previews;
         });
      });
    };

    //Advanced
    $scope.tenantVariables = VariablesService.query({tenantId: $stateParams.tenantId});
    $scope.recordVariables = VariablesService.get({tenantId: $stateParams.tenantId, recordId: $stateParams.tenantRecordId});

  }]);
});
