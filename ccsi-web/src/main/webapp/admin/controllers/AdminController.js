define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('AdminController', ['$scope', 'ngTableParams', 'TenantService',
    function($scope, ngTableParams, TenantService) {

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
  }]);
});
