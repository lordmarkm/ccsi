define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantHomeController', ['$scope', '$state', 'ngTableParams', 'TenantService', 'TemplateService', 'RecordService',
    function($scope, $state, ngTableParams, TenantService, TemplateService, RecordService) {

    //Handle tenants
    $scope.loadTenant = function (tenant) {
      console.debug('Loading tenant. name=' + tenant.name);
      $scope.tenant = tenant;
      $scope.statuses = TemplateService.query({tenantId: $scope.tenant.id}, function (templates) {
        if (templates.length) {
          $scope.record.status = templates[0];
        } else {
          $scope.record.status = undefined;
        }
      });
      $scope.reloadTable();
    }
    $scope.tenants = TenantService.query(function (tenants) {
      if (tenants.length) {
        $scope.loadTenant(tenants[0]);
      }
    });

    //Handle records
    $scope.totalRecords = 0;
    $scope.record = {};
    $scope.saveRecord = function (record) {
      if (!record) {
        record = $scope.record;
      }
      RecordService.save({tenantId: $scope.tenant.id}, record, function (record) {
        $scope.clearRecord();
        $scope.reloadTable();
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
      counts: [5,10,25,50,100], //determines pager
      getData: function($defer, params) {
        //Ajax request to backend resource
        params.$params.tenantId = $scope.tenant.id;
        RecordService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });
  }]);
});
