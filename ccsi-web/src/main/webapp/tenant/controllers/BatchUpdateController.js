define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('BatchUpdateController', ['$scope', '$state', '$stateParams', '$modal', 'ngTableParams', 'toaster', 'TenantService', 'TemplateService', 'RecordService',
                                                  function($scope, $state, $stateParams, $modal, ngTableParams, toaster, tenants, TenantService, TemplateService, RecordService) {

    console.debug('Loading tenant. id=' + $stateParams.tenantId);
    $scope.tenant = TenantService.get({tenantId: $stateParams.tenantId});

  }]);
});
