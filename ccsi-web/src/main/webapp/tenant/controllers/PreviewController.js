define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('PreviewController', ['$scope', '$stateParams', '$state', '$modal', 'toaster', 'templates', 'previews', 'tenantRecord', 'RecordService', 'PreviewService', 'VariablesService',
    function($scope, $stateParams, $state, $modal, toaster, templates, previews, tenantRecord, RecordService, PreviewService, VariablesService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.previews = previews;
    $scope.tenantRecord = tenantRecord;
    $scope.templates = templates;
    $scope.mobileNoPattern = /^639\d{9}$/;

    function reloadVariablesAndPreviews() {
      reloadVariables();
      reloadPreviews();
    }
    function reloadPreviews() {
      PreviewService.query({tenantId: $stateParams.tenantId, tenantRecordId: $stateParams.tenantRecordId}, function (previews) {
        $scope.previews = previews;
      });
    }
    $scope.saveRecord = function (record) {
      RecordService.save({tenantId: $stateParams.tenantId}, record, function () {
        toaster.pop('success', 'Update successful', 'Successfully updated tenant record.');
        reloadPreviews();
      }, function (fail) {
        toaster.pop('error', 'Update failed', fail.data);
      });
    };

    //Advanced
    //Broadcast number
    $scope.broadcastNo = $scope.tenantRecord.broadcastNo;
    $scope.saveBroadcastNo = function (valid) {
      if (!valid) {
        toaster.pop('error', 'Pattern error', 'Please enter a 12 digit mobile number following the pattern 639XXXXXXXXX');
        return;
      }
      $scope.tenantRecord.broadcastNo = $scope.broadcastNo;
      $scope.saveRecord($scope.tenantRecord);
    };

    //Variables
    $scope.tenantVariables = VariablesService.query({tenantId: $stateParams.tenantId});
    $scope.recordVariables = VariablesService.query({tenantId: $stateParams.tenantId, recordId: $stateParams.tenantRecordId});

    function reloadVariables() {
      $scope.recordVariables = VariablesService.query({tenantId: $stateParams.tenantId, recordId: $stateParams.tenantRecordId});
    }

    $scope.getRecordVariable = function (key) {
      var i = $scope.recordVariables.length;
      while (i--) {
        if ($scope.recordVariables[i].key === key) {
          return $scope.recordVariables[i];
        }
      }
      return {};
    };

    //What's being passed here is Tenant Variable, but what needs updating is the Record Variable
    $scope.updateVariable = function (variable) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-assign-variable',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.variable = variable;
          $scope.variableValue = {
              value: $scope.getRecordVariable(variable.key).value
          };
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            $scope.doUpdateRecordVariable(variable, $scope.variableValue.value);
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    $scope.doUpdateRecordVariable = function (tenantVariable, recordVarValue) {
      //allow empty strings here i think.
      var variable = $scope.getRecordVariable(tenantVariable.key);
      if (!variable.id) {
        variable = {
            key: tenantVariable.key,
            value: recordVarValue
        };
      } else {
        variable.value = recordVarValue;
      }
      VariablesService.save({tenantId: $stateParams.tenantId, recordId: $stateParams.tenantRecordId}, variable, function () {
        toaster.pop('success', 'Update successful', 'The variable with key ' + tenantVariable.key + ' has been updated to a new value.');
        reloadVariablesAndPreviews();
      });
    };
    $scope.clearVariable = function (tenantVariable) {
      var recordVariableId = $scope.getRecordVariable(tenantVariable.key).id;
      VariablesService.remove({tenantId: $stateParams.tenantId, variableId: recordVariableId}, function () {
        toaster.pop('success', 'Revert successful', 'The variable with key ' + tenantVariable.key + ' returned to its default value.');
        reloadVariablesAndPreviews();
      });
    };
  }]);
});
