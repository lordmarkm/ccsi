define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('TenantTemplatesController', ['$scope', '$stateParams', '$state', 'TemplateService',
    function($scope, $stateParams, $state, TemplateService) {

    $scope.templates = TemplateService.query({tenantId: $stateParams.tenantId});

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
        } else {
          angular.copy(saved, $scope.lastEdited);
        }
        $scope.clearTemplate();
      });
    };
    $scope.clearTemplate = function () {
      $scope.template = {};
    };
  }]);
});
