define([
  //"Root" controllers
  'tenant/controllers/AuthController.js',
  'tenant/controllers/NavSearchController.js',

  //Tenant
  'tenant/controllers/TenantCreateController.js',
  'tenant/controllers/TenantHomeController.js',
  'tenant/controllers/TenantSummaryController.js',
  'tenant/controllers/TenantController.js',
  'tenant/controllers/TenantTemplatesController.js',
  'tenant/controllers/StockTemplatesController.js',
  'tenant/controllers/VariablesController.js',
  'tenant/controllers/TenantTransactionsController.js',
  'tenant/controllers/PreviewController.js',
  'tenant/controllers/BroadcastController.js',
  'tenant/controllers/BatchUpdateController.js',

  //sidebar controllers
  'tenant/controllers/SidebarController.js',

  //admin controllers
  'admin/controllers/AdminController.js',
  'admin/controllers/AdminReportsController.js'
], function () {});