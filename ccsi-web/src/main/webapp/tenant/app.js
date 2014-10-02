define([
  'angular',
  'angular-animate',
  'angular-toaster',
  'bootstrap',
  'uiRouter',
  'uiBootstrap',
  'ngResource',
  'ngTable',
  'sugar',
  'tenant/controllers/controllers.js',
  'tenant/services.js',
  'tenant/filters.js'
], function (angular) {
  'use strict';
  return angular.module('op-app', [
    'ui.router',
    'ui.bootstrap',
    'ngResource',
    'ngAnimate',
    'toaster',
    'ngTable',
    'app.controllers',
    'app.services',
    'app.filters'
  ]);

});