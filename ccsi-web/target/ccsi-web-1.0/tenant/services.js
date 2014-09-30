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
    return $resource('template/:tenantId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('VariableService', function($resource) {
    return $resource('variable/:tenantId', {}, {
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
  });
});