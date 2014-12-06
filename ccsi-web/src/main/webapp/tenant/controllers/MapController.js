define(['tenant/controllers/module.js'], function (controllers) {
  'use strict';
  controllers.controller('MapController', ['$scope', '$state',
    function($scope, $state) {

    $scope.name = 'MapController';
    $scope.whatever = {name: 'helloworld'};

  });
