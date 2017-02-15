define(["backbone", "model/ForecastEntry"], function (Backbone, ForecastEntry) {
  return Backbone.Collection.extend({
    model: ForecastEntry,
    url: '/api/v1/forecast_entries/'
  });
});