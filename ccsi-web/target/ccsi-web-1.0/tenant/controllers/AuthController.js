define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('AuthController', ['$scope', '$state', '$location', 'AuthService',
    function($scope, $state, $location, AuthService) {

    $scope.principal = AuthService.get(function (auth) {
      //console.debug('Location.path(): [' + $location.path() + ']');
      if (!$location.path()) {
        $state.go('home');
      }
    });

  }]);
});
