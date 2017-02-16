define(["backbone"], function (Backbone) {
  return Backbone.Model.extend({
    urlRoot: '/api/v1/forecast_entries/',
    defaults: {
      monthlyAmounts: [{
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear()
      }]
    }
  });
});
