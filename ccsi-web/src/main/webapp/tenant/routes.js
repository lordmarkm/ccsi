define([
    'tenant/app.js',
    'tenant/resolve/TenantResolve.js',
    'tenant/resolve/PreviewResolve.js',
    'admin/resolve/AdminResolve.js'
  ], function(app, TenantResolve, PreviewResolve, AdminResolve) {
  'use strict';
  return app.config(function($stateProvider) {
    $stateProvider.state('home', {
      url: '/?tenantIndex',
      templateUrl: 'tenant/view/home.html',
      controller: 'TenantHomeController',
      resolve: TenantResolve
    })
    .state('tenant_create', {
      url: '/tenant/create?tenantIndex',
      templateUrl: 'tenant/view/create.html',
      controller: 'TenantCreateController'
    })
    .state('tenant', {
      url: '/tenant/{tenantId}',
      templateUrl: 'tenant/view/tenant.html',
      controller: 'TenantController'
    })
    .state('tenant.templates', {
      url: '/templates?tenantIndex',
      templateUrl: 'tenant/view/templates.html',
      controller: 'TenantTemplatesController'
    })
    .state('tenant.stock_templates', {
      url: '/stock?tenantIndex',
      templateUrl: 'tenant/view/stock_templates.html',
      controller: 'StockTemplatesController'
    })
    .state('tenant.variables', {
      url: '/variables?tenantIndex',
      templateUrl: 'tenant/view/variables.html',
      controller: 'VariablesController'
    })
    .state('tenant.transactions', {
      url: '/transactions?tenantIndex',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    })
    .state('tenant.record_txns', {
      url: '/recordtxn/{tenantRecordId}?tenantIndex',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    })
    .state('tenant.record_preview', {
      url: '/preview/{tenantRecordId}?tenantIndex',
      templateUrl: 'tenant/view/preview.html',
      controller: 'PreviewController',
      resolve: PreviewResolve
    })
    .state('tenant.broadcast', {
      url: '/broadcast?tenantIndex',
      templateUrl: 'tenant/view/broadcast.html',
      controller: 'BroadcastController'
    })
    .state('tenant.batchupdate', {
      url: '/batch?tenantIndex',
      templateUrl: 'tenant/view/batchupdate.html',
      controller: 'BatchUpdateController'
    })
    //admin states
    .state('admin', {
      url: '/admin',
      templateUrl: 'admin/view/index.html',
      controller: 'AdminController',
      resolve: AdminResolve
    })
    .state('admin_txn', {
      url: '/transactions',
      templateUrl: 'admin/view/transactions.html',
      controller: 'AdminReportsController'
    })
  });
});