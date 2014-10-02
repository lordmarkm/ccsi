define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('TenantHomeController', ['$scope', '$state', '$stateParams', '$modal', 'ngTableParams', 'toaster', 'tenants', 'TenantService', 'TemplateService', 'RecordService',
    function($scope, $state, $stateParams, $modal, ngTableParams, toaster, tenants, TenantService, TemplateService, RecordService) {

    $scope.tenants = tenants;

    //Handle record filtering
    $scope.filter = {};
    $scope.clearFilter = function () {
      $scope.filter = {};
      $scope.reloadTable();
    };

    //Handle tenants
    $scope.loadTenant = function (tenant) {
      $scope.tenant = tenant;
      $scope.statuses = TemplateService.query({tenantId: $scope.tenant.id}, function (templates) {
        if (templates.length) {
          $scope.record.status = templates[0];
        } else {
          $scope.record.status = undefined;
        }
      });
      $scope.reloadTable();
      $scope.tenantIndex = findIndex(tenant);
    };
    function findIndex(tenant) {
      var i = tenants.length;
      while (i--) {
        if (tenants[i].id === tenant.id) {
          return i;
        }
      }
      return 0;
    }

    //Handle records
    $scope.totalRecords = 0;
    $scope.record = {};
    $scope.updateRecord = function (record) {
      if (!record) {
        //create new
        $scope.clearRecord();
      } else {
        //update
        $scope.record = angular.copy(record);
      }
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-create-record',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            $scope.saveRecord();
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    $scope.saveRecord = function (record) {
      if (!record) {
        record = $scope.record;
      }
      RecordService.save({tenantId: $scope.tenant.id}, record, function (saved) {
        $scope.clearRecord();
        $scope.reloadTable();
        toaster.pop('success', record.id ? 'Record updated' : 'Record created', 'Successfully saved record with tracking number ' + saved.trackingNo + '.');
      }, function (fail) {
        toaster.pop('error', 'Save failed', fail.data);
      });
    };
    $scope.clearRecord = function () {
      $scope.record = {};
      if ($scope.statuses.length) {
        $scope.record.status = $scope.statuses[0];
      }
    };
    $scope.reloadTable = function () {
      if ($scope.tableParams.page() == 1) {
        $scope.tableParams.reload();
      } else {
        $scope.tableParams.page(1);
      }
    };
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
        //Inject some additional filtering parameters
        params.$params.tenantId = $scope.tenant.id;
        params.$params.status = $scope.filter.status;
        params.$params.trackingNo = $scope.filter.trackingNo;
        params.$params.customerName = $scope.filter.customerName;
        params.$params.transactionType = $scope.filter.transactionType;
        RecordService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });

    if (tenants.length) {
      var index = $stateParams.tenantIndex && $stateParams.tenantIndex < tenants.length ? $stateParams.tenantIndex : 0;
      if ($stateParams.tenantIndex === '-1') index = tenants.length - 1;
      $scope.loadTenant(tenants[index]);
    }
  }]);
});
