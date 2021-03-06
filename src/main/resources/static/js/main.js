require.config({
  baseUrl: "/js",
  paths: {
    "backbone" : "lib/backbone",
    "backbone.paginator" : "lib/backbone.paginator",
    "big" : "lib/big.min",
    "bootstrap" : "lib/bootstrap",
    "bootstrap-modal": "lib/backbone.bootstrap-modal",
    "chart" : "lib/Chart",
    "chart2": "lib/Chart2",
    "jquery" : "lib/jquery-2.1.4",
    "moment" : "lib/moment",
    "please" : "lib/Please",
    "template" : "/template",
    "text" : "lib/text",
    "tiny-color" : "lib/tinycolor",
    "tpl": "lib/tpl",
    "underscore" : "lib/underscore"
  },
  waitSeconds: 0
});

require(['router'], function () {
  Backbone.history.start({pushState: true});
});
