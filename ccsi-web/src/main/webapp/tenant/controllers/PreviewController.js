define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('PreviewController', ['$scope', '$stateParams', '$state', 'templates', 'previews', 'tenantRecord', 'RecordService', 'PreviewService',
    function($scope, $stateParams, $state, templates, previews, tenantRecord, RecordService, PreviewService) {

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
  }]);
});
