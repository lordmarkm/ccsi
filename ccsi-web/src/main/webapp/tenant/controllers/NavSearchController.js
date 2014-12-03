define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('NavSearchController', ['$rootScope', '$scope', '$state', 'RecordService',
    function($rootScope, $scope, $state, RecordService) {

    //Testing directive
    $scope.typeahead = '';

    //Handle tenant switch from sidebar
    $rootScope.$on('loadTenant', function(evt, loadEvent) {
      $scope.tenant = loadEvent.tenant;
    });
    //Also broadcast by the sidebar, but only when a tenant has already been loaded
    $rootScope.$on('setTenant', function(evt, tenant) {
      $scope.tenant = tenant;
    });

    $scope.searchCustomers = function (query) {
      if (!$scope.tenant) {
        return;
      }
      var filter = {
          page: 1,
          count: 10,
          tenantId: $scope.tenant.id,
          trackingNo: query,
          customerName: query,
          transactionType : query,
          navbar: "true"
      };
      return RecordService.page(filter).$promise.then(function(response) {
        return response.data;
      });
    };

    $scope.gotoPreview = function (record) {
      if (!$scope.tenant) {
        return;
      }
      $state.go('record.preview', {tenantId: $scope.tenant.id, tenantRecordId: record.id});
      return record.customerName;
    };

  }]);
});
