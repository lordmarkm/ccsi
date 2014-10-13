define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('AdminController', ['$scope', '$http', '$modal', 'toaster', 'ngTableParams', 'TenantService',
    function($scope, $http, $modal, toaster, ngTableParams, TenantService) {

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
        //params.$params.tenantId = $scope.tenant.id;
        TenantService.page(params.$params, function(response) {
          params.total(response.total);
          $defer.resolve(response.data);
          $scope.totalRecords = response.total;
        });
      }
    });

    //Control reply charging schemes
    $scope.replyChargingSchemes = ['FREE', 'PISO', 'TWO_FIFTY', 'FIVE'];
    $scope.schemeDescription = {
        'FREE': 'Smart: free, Globe: free, Sun: P2.00',
        'PISO': 'Smart: P1.00, Globe: P1.00, Sun: P2.00',
        'TWO_FIFTY': 'Smart: P2.50, Globe: P2.50, Sun: P2.00',
        'FIVE': 'Smart: P5.00, Globe: P5.00, Sun: P2.00'
    };
    $scope.saveTenant = function (tenant) {
      TenantService.save(tenant, function (tenant) {
        toaster.pop('success', 'Save success', 'Tenant ' + tenant.name + ' updated.');
      });
    };

    //Give/take credits
    $scope.giveCredits = function (tenant) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-give-credits',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.tenant = tenant;
          $scope.giveCredits = {pushCredits:1};
          $scope.confirm = function () {
            console.debug('Giving ' + $scope.giveCredits.pushCredits + ' credits to ' + $scope.tenant.name);
            $http.post('admin/credits/' + $scope.tenant.id + '/' + $scope.giveCredits.pushCredits).success(function (newbalance) {
              toaster.pop('success', 'Update success', 'Credits successfully added');
              $scope.tenant.pushCredits = newbalance;
            });
            $modalInstance.dismiss('ok');
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    $scope.takeCredits = function (tenant) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-take-credits',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.tenant = tenant;
          $scope.takeCredits = {pushCredits:1};
          $scope.confirm = function () {
            console.debug('Taking ' + $scope.takeCredits.pushCredits + ' credits from ' + $scope.tenant.name);
            $http.delete('admin/credits/' + $scope.tenant.id + '/' + $scope.takeCredits.pushCredits).success(function (newbalance) {
              toaster.pop('success', 'Update success', 'Credits successfully taken away');
              $scope.tenant.pushCredits = newbalance;
            });
            $modalInstance.dismiss('ok');
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
  }]);
});
