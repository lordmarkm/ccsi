define(['angular', 'tenant/controllers/module.js'], function (angular, controllers) {
  'use strict';
  controllers.controller('MapController', ['$scope', '$state',
    function($scope, $state) {


    $scope.name = 'MapController';
    $scope.whatever = {name: 'helloworld'};

  }]);
});
