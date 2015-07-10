require.config({
  baseUrl: "/js",
  jsx: {
    fileExtension: '.jsx'
  },
  paths: {
    "backbone" : "lib/backbone",
    "jquery" : "lib/jquery-2.1.4",
    "moment" : "lib/moment",
    "template" : "/template",
    "text" : "lib/text",
    "tpl": "lib/tpl",
    "underscore" : "lib/underscore"
  }
});

require(['router'], function () {
  Backbone.history.start({pushState: true});
});