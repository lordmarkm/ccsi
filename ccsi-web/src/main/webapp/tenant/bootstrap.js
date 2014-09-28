define([
  'require',
  'angular',
  'tenant/app.js',
  'tenant/routes.js'
], function (require, angular) {
  'use strict';
  require(['domReady!'], function (document) {
    angular.bootstrap(document, ['op-app']);
  });
});