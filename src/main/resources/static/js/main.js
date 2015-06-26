require.config({
  baseUrl: "/js",
  jsx: {
    fileExtension: '.jsx'
  },
  paths: {
    "backbone" : "/js/lib/backbone",
    "JSXTransformer" : "/js/lib/JSXTransformer",
    "jquery" : "/js/lib/jquery-2.1.4",
    "jsx" : "/js/lib/jsx",
    "moment" : "/js/lib/moment",
    "react" : "/js/lib/react-with-addons",
    "text" : "/js/lib/text",
    "underscore" : "/js/lib/underscore"
    
  }
});

require(['jsx!router'], function () {
  Backbone.history.start();
});