define(['tenant/app.js'], function(app) {
  'use strict';
  return app.config(function($stateProvider) {
    $stateProvider.state('home', {
      url: '/',
      templateUrl: 'tenant/view/home.html',
      controller: 'TenantHomeController'
    })
    .state('tenant_create', {
      url: '/tenant/create',
      templateUrl: 'tenant/view/create.html',
      controller: 'TenantCreateController'
    });
  });
});