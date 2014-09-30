define([
    'tenant/app.js',
    'tenant/resolve/TenantResolve.js'
  ], function(app, TenantResolve) {
  'use strict';
  return app.config(function($stateProvider) {
    $stateProvider.state('home', {
      url: '/?tenantIndex',
      templateUrl: 'tenant/view/home.html',
      controller: 'TenantHomeController',
      resolve: TenantResolve
    })
    .state('tenant_create', {
      url: '/tenant/create',
      templateUrl: 'tenant/view/create.html',
      controller: 'TenantCreateController'
    })
    .state('tenant', {
      url: '/tenant/{tenantId}',
      templateUrl: 'tenant/view/tenant.html',
      controller: 'TenantController'
    })
    .state('tenant.templates', {
      url: '/templates',
      templateUrl: 'tenant/view/templates.html',
      controller: 'TenantTemplatesController'
    })
    .state('tenant.transactions', {
      url: '/transactions',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    })
    .state('tenant.record_txns', {
      url: '/recordtxn/{tenantRecordId}',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    });
  });
});