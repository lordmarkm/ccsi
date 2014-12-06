define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('NavSearchController', ['$rootScope', '$scope', '$modal', 'toaster', '$state', 'RecordService', 'OrgService', 'EventService',
    function($rootScope, $scope, $modal, toaster, $state, RecordService, OrgService, EventService) {

    //Testing directive
    $scope.typeahead = '';

    //Handle tenant switch from sidebar
    $rootScope.$on('loadTenant', function(evt, loadEvent) {
      $scope.tenant = loadEvent.tenant;
    });
    //Also broadcast by the sidebar, but only when a tenant has already been loaded
    $rootScope.$on('setTenant', function(evt, tenant) {
      $scope.tenant = tenant;
    });

    $scope.neworg = {};
    $scope.createOrg = function () {
      console.debug("wat");
      return $modal.open({
        scope: $scope,
        templateUrl: 'modal-create-organization',
        backdrop: 'static',
        controller: ['$scope', '$modalInstance', function ($scope, $modalInstance) {
          $scope.confirm = function () {
            doCreate($scope.neworg);
            $modalInstance.dismiss('ok');
          };
          $scope.close = function () {
            $modalInstance.dismiss('ok');
          };
        }]
      });
    };
    function doCreate(org) {
      OrgService.save(org, function (response) {
        toaster.pop('success', 'Organization created', 'Successfully created organization');
      });
    }

    $scope.orgId = 'ALL';
    $scope.orgs = OrgService.query();
    $scope.updateOrg = function () {
      $scope.events = EventService.query({orgId: $scope.orgId === 'ALL' ? -1 : $scope.orgId});
    };

    $scope.searchCustomers = function (query) {
      if (!$scope.tenant) {
        return;
      }
      var filter = {
          page: 1,
          count: 10,
          tenantId: $scope.tenant.id,
          trackingNo: query,
          customerName: query,
          transactionType : query,
          navbar: "true"
      };
      return RecordService.page(filter).$promise.then(function(response) {
        return response.data;
      });
    };

    $scope.gotoPreview = function (record) {
      if (!$scope.tenant) {
        return;
      }
      $state.go('record.preview', {tenantId: $scope.tenant.id, tenantRecordId: record.id});
      return record.customerName;
    };

  }]);
});
