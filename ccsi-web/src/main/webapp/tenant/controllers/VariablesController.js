define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('VariablesController', ['$scope', '$stateParams', '$state', '$modal', 'toaster', 'VariablesService',
    function($scope, $stateParams, $state, $modal, toaster, VariablesService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.variables = VariablesService.query({tenantId: $stateParams.tenantId});

    function reloadVariables() {
      $scope.variables = VariablesService.query({tenantId: $stateParams.tenantId});
    };

    //Templates
    $scope.variable = {};
    $scope.findVariableByKey = function (key) {
      var i = $scope.variables.length;
      while (i--) {
        if ($scope.variables[i].key === key) {
          return $scope.variables[i];
        }
      }
    };
    $scope.editVariable = function (variable) {
      $scope.variable = angular.copy(variable);
      $scope.lastEdited = variable;
    };
    $scope.saveVariable = function () {
      VariablesService.save({tenantId: $stateParams.tenantId}, $scope.variable, function (saved) {
        //If it had an id before being saved, operation was an update, else it was a new template
        if (!$scope.variable.id) {
          $scope.variables.push(saved);
          toaster.pop('success', 'Variable saved', 'Variable with key "' + saved.key + '" created.');
        } else {
          angular.copy(saved, $scope.lastEdited);
          toaster.pop('success', 'Variable saved', 'Variable with key "' + saved.key + '" updated.');
        }
        $scope.clearVariable();
      });
    };
    $scope.deleteVariable = function (variable) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-delete-variable',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.toDelete = variable;
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            VariablesService.remove({tenantId: $scope.tenant.id, variableId: variable.id}, function (status) {
              if ($scope.variable.id === variable.id) {
                $scope.clearVariable();
              }
              reloadVariables();
              toaster.pop('success', 'Delete successful', 'Successfully deleted variable with key ' + variable.key);
            }, function (failMsg) {
              toaster.pop('error', 'Error performing delete', failMsg.data);
            });
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    $scope.clearVariable = function () {
      $scope.variable = {};
    };
  }]);
});
