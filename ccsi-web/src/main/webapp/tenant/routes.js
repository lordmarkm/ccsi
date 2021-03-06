define([
    'tenant/app.js',
    'tenant/resolve/TenantResolve.js',
    'tenant/resolve/TenantSummaryResolve.js',
    'tenant/resolve/PreviewResolve.js',
    'admin/resolve/AdminResolve.js',
    'tenant/resolve/SidebarResolve.js'
  ], function(app, TenantResolve, TenantSummaryResolve, PreviewResolve, AdminResolve, SidebarResolve) {
  'use strict';
  return app.config(function($stateProvider) {
    $stateProvider.state('home', {
      url: '/?tenantIndex',
      views: {
        main: {
          templateUrl: 'tenant/view/home.html',
          controller: 'TenantHomeController',
          resolve: TenantResolve
        },
        sidebar: {
          templateUrl: 'tenant/view/sidebar.html',
          controller: 'SidebarController',
          resolve: SidebarResolve
        }
      }
    })
    .state('tenant_create', {
      url: '/tenant/create?tenantIndex',
      views: {
        main: {
          templateUrl: 'tenant/view/create.html',
          controller: 'TenantCreateController'
        },
        sidebar: {
          templateUrl: 'tenant/view/create_sidebar.html'
        }
      }
    })
    .state('tenant', {
      url: '/tenant/{tenantId}',
      views: {
        main: {
          templateUrl: 'tenant/view/tenant.html',
          controller: 'TenantController'
        },
        sidebar: {
          templateUrl: 'tenant/view/sidebar.html',
          controller: 'SidebarController',
          resolve: SidebarResolve
        }
      }
    })
    .state('tenant.summary', {
      url: '/summary?tenantIndex',
      templateUrl: 'tenant/view/summary.html',
      controller: 'TenantSummaryController',
      resolve: TenantSummaryResolve
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
    .state('tenant.transactions', {
      url: '/transactions?tenantIndex',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    })
    .state('tenant.variables', {
      url: '/variables?tenantIndex',
      templateUrl: 'tenant/view/variables.html',
      controller: 'VariablesController'
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

    //states involving customer records
    .state('record', {
      url: '/tenant/{tenantId}/record/{tenantRecordId}',
      views: {
        main: {
          template: '<ui-view></ui-view>',
          controller: 'TenantController'
        },
        sidebar: {
          templateUrl: 'tenant/view/sidebar.html',
          controller: 'SidebarController',
          resolve: SidebarResolve
        }
      },
      abstract: true
    })
    .state('record.txns', {
      url: '/txns',
      templateUrl: 'tenant/view/transactions.html',
      controller: 'TenantTransactionsController'
    })
    .state('record.preview', {
      url: '/preview',
      templateUrl: 'tenant/view/preview.html',
      controller: 'PreviewController',
      resolve: PreviewResolve
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