define(['angular'], function(angular) {
  'use strict';
  angular.module('app.filters', [])

  .filter('name', function() {
    return function(nameInfo) {
      return nameInfo ? nameInfo.givenName + ' ' + nameInfo.surname : '';
    };
  })
  .filter('commaJoin', function() {
    return function(array, prop) {
      if (!array) return '';
      var i = array.length, props = [];
      while (i--) {
        props.push(array[i][prop]);
      }
      return props.join(',');
    };
  });
});
