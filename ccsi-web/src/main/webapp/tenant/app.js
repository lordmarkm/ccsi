define([
  'angular',
  'bootstrap',
  'uiRouter',
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
    'ngResource',
    'ngTable',
    'app.controllers',
    'app.services',
    'app.filters'
  ]);

});