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
    })
    .state('tenant', {
      url: '/tenant/{tenantId}',
      controller: 'TenantController'
    })
    .state('tenant.templates', {
      url: '/templates',
      templateUrl: 'tenant/view/templates.html',
      controller: 'TenantTemplatesController'
    });
  });
});