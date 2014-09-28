define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('TenantTemplatesController', ['$scope', '$stateParams', '$state', 'TemplateService',
    function($scope, $stateParams, $state, TemplateService) {

    console.debug('loading templates');
    $scope.templates = TemplateService.query({tenantId: $stateParams.tenantId});

  }]);
});
