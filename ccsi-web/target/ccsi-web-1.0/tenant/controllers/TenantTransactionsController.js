define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantTransactionsController', ['$scope', '$state', '$stateParams', 'ngTableParams', 'RecordService', 'TransactionRecordService',
    function($scope, $state, $stateParams, ngTableParams, RecordService, TransactionRecordService) {

    $scope.tenantIndex = $stateParams.tenantIndex;

    var 
        tenantId = $stateParams.tenantId,
        tenantRecordId = $stateParams.tenantRecordId;

    if ($stateParams.tenantRecordId) {
      $scope.tenantRecord = RecordService.get({tenantId: tenantId, tenantRecordId: tenantRecordId});
    }

    $scope.tableParams = new ngTableParams({
      page: 1,
      count: 5,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      counts: [5,10,25,50,100],
      getData: function($defer, params) {
        params.$params.tenantId = tenantId;
        if ($stateParams.tenantRecordId) {
          params.$params.tenantRecordId = tenantRecordId;
        }
        TransactionRecordService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });

    $scope.reloadTable = function () {
      if ($scope.tableParams.page() == 1) {
        $scope.tableParams.reload();
      } else {
        $scope.tableParams.page(1);
      }
    };
  }]);
});
