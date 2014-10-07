define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('AuthController', ['$scope', '$state', '$location', 'AuthService',
    function($scope, $state, $location, AuthService) {

    $scope.hasRole = function (role) {
      if (!$scope.auth) {
        return false;
      }
      for (var i in $scope.auth.authorities) {
        if ($scope.auth.authorities[i].authority === role) {
          return true;
        }
      }
      return false;
    }

    $scope.principal = AuthService.get(function (auth) {
      //console.debug('Location.path(): [' + $location.path() + ']');
      $scope.auth = auth;
      if (!$location.path()) {
        if ($scope. hasRole('ROLE_ADMIN')) {
          $state.go('admin');
        } else {
          $state.go('home');
        }
      }
    });

  }]);
});
