define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('AuthController', ['$scope', '$state', 'AuthService',
    function($scope, $state, AuthService) {

    $scope.principal = AuthService.get();

  }]);
});
