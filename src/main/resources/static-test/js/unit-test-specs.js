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
    "underscore" : "lib/underscore",

    "chai": "lib/test/chai",
    "mocha": "lib/test/mocha",
    "sinon": "lib/test/sinon-1.17.7"
  },
  waitSeconds: 0,
  shim: {
    'mocha': {
      deps: [ 'jquery' ],
      exports: 'mocha'
    }
  },
  map: {
    '*': {
      'router': 'mockRouter'
    }
  }
});

define(['mocha'], function (mocha) {
  mocha.setup({
    slow: 300,
    ui: 'bdd'
  });

  require([
    'spec/model/ForecastEntry',
    'spec/view/forecast/Home'
  ], function () {
    mocha.run();
  });
});
