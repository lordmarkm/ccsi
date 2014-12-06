// This file holds all the constants used in the Demos.
(function (exports, ctx) {
  exports.HereMapsConstants = {
    //  Set authentication token and appid
    //  WARNING: this is a demo-only key
    //
    // Add your own appId and token here
    // sign in and register on http://developer.here.com
    // and obtain your own developer's API key
    AppIdAndToken :{
      appId: 'yuUv19zXRpzc7wZchZ5c',
      appCode: 'OWLzyB_tglXVzu5ecSD32A',
      language: 'en-US',
      serviceMode: 'cit'
    },
    // Initial center and zoom level of the map
    InitialLocation : {
      longitude: 14.65,
      latitude:  121.064,
      zoomLevel: 14},

    JSLibs  :{
      // versioned URL to load the HERE maps API.
      // Check on:  http://developer.here.com/versions
      // to obtain the latest version.
      HereMapsUrl :'http://js.cit.api.here.com/se/2.5.4/jsl.js?blank=true',
      // versioned URL to load jQuery
      jQueryUrl : 'http://code.jquery.com/jquery-1.10.1.min.js',
      jQueryUIUrl: 'http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js'
    },
    NS : 'nokia'

  }
})(window, document);