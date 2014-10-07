define(['angular'], function(angular) {
  'use strict';

  angular.module('app.services', ['ngResource'])

  .factory('AuthService', function($resource) {
    return $resource('auth');
  })
  .factory('TenantService', function($resource) {
    return $resource('tenants/:tenantId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('TemplateService', function($resource) {
    return $resource('template/:tenantId/:templateId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('StockTemplateService', function($resource) {
    return $resource('stock/:tenantId/:stockId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('VariablesService', function($resource) {
    return $resource('variable/:tenantId/:recordId/:variableId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  //This is for tenant recods
  .factory('RecordService', function($resource) {
    return $resource('record/:tenantId/:tenantRecordId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('TransactionRecordService', function($resource) {
    return $resource('txn/:tenantId/:tenantRecordId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('PreviewService', function($resource) {
    return $resource('preview/:tenantId/:tenantRecordId');
  })
  .factory('BroadcastService', function($resource) {
    return $resource('broadcast/:tenantId');
  });
});