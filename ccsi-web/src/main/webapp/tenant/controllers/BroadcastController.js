define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('BroadcastController', ['$scope', '$state', '$stateParams', '$modal', 'ngTableParams', 'toaster', 'TenantService', 'TemplateService', 'RecordService', 'StockTemplateService', 'BroadcastService',
    function($scope, $state, $stateParams, $modal, ngTableParams, toaster, TenantService, TemplateService, RecordService, StockTemplateService, BroadcastService) {

    $scope.tenant = TenantService.get({tenantId: $stateParams.tenantId});
    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.record = {};
    $scope.statuses = TemplateService.query({tenantId: $stateParams.tenantId}, function (templates) {
      if (templates.length) {
        $scope.record.status = templates[0];
      } else {
        $scope.record.status = undefined;
      }
    });
    $scope.stocks = StockTemplateService.query({tenantId: $stateParams.tenantId}, function (stocks) {
      if (stocks.length) {
        $scope.stockBroadcast = stocks[0].keyword;
      }
    });

    //Handle record filtering
    $scope.filter = {};
    $scope.lastfilter = {};
    $scope.clearFilter = function () {
      $scope.filter = {};
      $scope.reloadTable();
    };

    //Handle records
    $scope.totalRecords = 0;
    $scope.record = {};
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
      $scope.lastfilter = angular.copy($scope.filter);
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
        params.$params.tenantId = $stateParams.tenantId;
        params.$params.status = $scope.filter.status;
        params.$params.trackingNo = $scope.filter.trackingNo;
        params.$params.customerName = $scope.filter.customerName;
        params.$params.transactionType = $scope.filter.transactionType;
        params.$params.requireBroadcastNo = 'true';
        RecordService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });

    $scope.broadcast = function (type) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-confirm-broadcast',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.type = type;
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            doBroadcast(type);
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    function doBroadcast(broadcastType) {
      if ($scope.totalRecords > $scope.tenant.pushCredits) {
        toaster.pop('error', 'Insufficient push credits', 'Not enough push credits to complete this broadcast.');
        return;
      }
      BroadcastService.save({
        tenantId: $stateParams.tenantId,
        broadcastType: broadcastType,
        stockBroadcast: $scope.stockBroadcast,
        customBroadcast: $scope.customBroadcast,
        status: $scope.lastfilter.status,
        trackingNo : $scope.lastfilter.trackingNo,
        customerName : $scope.lastfilter.customerName,
        transactionType : $scope.lastfilter.transactionType
      },{

      }, function (success) {
        toaster.pop('success', 'Successful broadcast', $scope.totalRecords + ' messages successfully sent.');
        $scope.tenant.pushCredits = success.message;
      }, function (fail) {
        toaster.pop('error', 'Broadcast failed', fail.data);
      });
    }

  }]);
});
