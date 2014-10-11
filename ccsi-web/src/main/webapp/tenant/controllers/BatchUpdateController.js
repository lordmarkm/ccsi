define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('BatchUpdateController', ['$scope', '$state', '$stateParams', '$modal', 'ngTableParams', 'toaster', 'TenantService', 'TemplateService', 'RecordService',
                                                  function($scope, $state, $stateParams, $modal, ngTableParams, toaster, TenantService, TemplateService, RecordService) {

    $scope.tenant = TenantService.get({tenantId: $stateParams.tenantId});
    $scope.statuses = TemplateService.query({tenantId: $stateParams.tenantId}, function (templates) {
      if (templates.length) {
        $scope.newStatus = templates[0].status;
      }
    });

    //Handle record filtering
    $scope.filter = {};
    $scope.lastfilter = {};
    $scope.clearFilter = function () {
      $scope.filter = {};
      $scope.reloadTable();
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
        RecordService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });

    $scope.update = function () {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-confirm-batchupdate',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            doUpdate();
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };

    function doUpdate() {
      RecordService.save({
        tenantId: $stateParams.tenantId,
        status: $scope.lastfilter.status,
        trackingNo: $scope.lastfilter.trackingNo,
        customerName: $scope.lastfilter.customerName,
        transactionType: $scope.lastfilter.transactionType,
        newStatus: $scope.newStatus
      }, {}, function (resp) {
        toaster.pop('success', 'Save success.', resp.message + ' records updated.');
        $scope.reloadTable();
      }, function () {
        toaster.pop('error', 'Save failed.');
      });
    }

  }]);
});
