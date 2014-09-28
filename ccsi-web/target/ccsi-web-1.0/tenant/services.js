define(['angular'], function(angular) {
  'use strict';

  angular.module('app.services', ['ngResource'])

  .factory('AuthService', function($resource) {
    return $resource('/auth');
  })
  .factory('TenantService', function($resource) {
    return $resource('/tenant', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('TemplateService', function($resource) {
    return $resource('/template/:tenantId', {}, {
      page: {method: 'GET', isArray: false}
    });
  })
  .factory('VariableService', function($resource) {
    return $resource('/variable/:tenantId', {}, {
      page: {method: 'GET', isArray: false}
    });
  });

});