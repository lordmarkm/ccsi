define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('TenantTemplatesController', ['$scope', '$stateParams', '$state', '$modal', 'toaster', 'StockTemplateService', 'TemplateService',
    function($scope, $stateParams, $state, $modal, toaster, StockTemplateService, TemplateService) {

    $scope.tenantIndex = $stateParams.tenantIndex;
    $scope.templates = TemplateService.query({tenantId: $stateParams.tenantId});

    function reloadTemplates() {
      $scope.templates = TemplateService.query({tenantId: $stateParams.tenantId});
    };

    //Templates
    $scope.template = {};
    $scope.findTemplateByStatus = function (status) {
      var i = $scope.templates.length;
      while (i--) {
        if ($scope.templates[i].status === status) {
          return $scope.templates[i];
        }
      }
    };
    $scope.editTemplate = function (template) {
      $scope.template = angular.copy(template);
      $scope.lastEdited = template;
    };
    $scope.saveTemplate = function () {
      TemplateService.save({tenantId: $stateParams.tenantId}, $scope.template, function (saved) {
        //If it had an id before being saved, operation was an update, else it was a new template
        if (!$scope.template.id) {
          $scope.templates.push(saved);
          toaster.pop('success', 'Template saved', 'Template with status "' + saved.status + '" created.');
        } else {
          angular.copy(saved, $scope.lastEdited);
          toaster.pop('success', 'Template saved', 'Template with status "' + saved.status + '" updated.');
        }
        $scope.clearTemplate();
      });
    };
    $scope.deleteTemplate = function (template) {
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-delete-template',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.toDelete = template;
          $scope.confirm = function () {
            $modalInstance.dismiss('ok');
            TemplateService.remove({tenantId: $scope.tenant.id, templateId: template.id}, function (status) {
              if ($scope.template.id === template.id) {
                $scope.clearTemplate();
              }
              reloadTemplates();
              toaster.pop('success', 'Delete successful', 'Successfully deleted template with status ' + template.status);
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
    $scope.clearTemplate = function () {
      $scope.template = {};
    };
  }]);
});
