define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('StockTemplatesController', ['$scope', '$stateParams', '$state', '$modal', 'toaster', 'StockTemplateService',
    function($scope, $stateParams, $state, $modal, toaster, StockTemplateService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.stocks = StockTemplateService.query({tenantId: $stateParams.tenantId});

    function reloadStocks() {
      $scope.stocks = StockTemplateService.query({tenantId: $stateParams.tenantId});
    }

    //Stock templates
    $scope.stock = {};
    $scope.findStockByKeyword = function (keyword) {
      var i = $scope.stocks.length;
      while (i--) {
        if ($scope.stocks[i].keyword === keyword) {
          return $scope.stocks[i];
        }
      }
    };
    $scope.editStock = function (stock) {
      $scope.stock = angular.copy(stock);
      $scope.lastEdited = stock;
    };
    $scope.saveStock = function () {
      StockTemplateService.save({tenantId: $stateParams.tenantId}, $scope.stock, function (saved) {
        //If it had an id before being saved, operation was an update, else it was a new template
        if (!$scope.stock.id) {
          $scope.stocks.push(saved);
          toaster.pop('success', 'Stock Template saved', 'Stock Template with keyword "' + saved.keyword + '" created.');
        } else {
          angular.copy(saved, $scope.lastEdited);
          toaster.pop('success', 'Stock Template saved', 'Stock Template with keyword "' + saved.keyword + '" updated.');
        }
        $scope.clearStock();
      }, function (response) {
        toaster.pop('error', 'Save failed', response.data);
      });
    };
    $scope.deleteStock = function (stock) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-delete-stock',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.toDelete = stock;
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            StockTemplateService.remove({tenantId: $scope.tenant.id, stockId: stock.id}, function (status) {
              if ($scope.stock.id === stock.id) {
                $scope.clearStock();
              }
              reloadStocks();
              toaster.pop('success', 'Delete successful', 'Successfully deleted keyword template with keyword ' + stock.keyword);
            });
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    $scope.clearStock = function () {
      $scope.stock = {};
    };
  }]);
});
