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
    $stateProvider
    
    .state('gems', {
      url: '/gems',
      views: {
        main: {
          templateUrl: 'here/view/map.html',
          controller: 'MapController'
        },
        sidebar: {
          templateUrl: 'here/view/sidebar.html'
        }
      }
    })


  });
});