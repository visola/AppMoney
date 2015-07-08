require.config({
  baseUrl: "/js",
  jsx: {
    fileExtension: '.jsx'
  },
  paths: {
    "backbone" : "lib/backbone",
    "jquery" : "lib/jquery-2.1.4",
    "JSXTransformer" : "lib/JSXTransformer",
    "jsx" : "lib/jsx",
    "moment" : "lib/moment",
    "react" : "lib/react-with-addons",
    "template" : "/template",
    "text" : "lib/text",
    "tpl": "lib/tpl",
    "underscore" : "lib/underscore"
  }
});

require(['jsx!router'], function () {
  Backbone.history.start({pushState: true});
});