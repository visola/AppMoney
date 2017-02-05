define(["backbone", "model/CategoryForecastEntry"], function (Backbone, CategoryForecastEntry) {
  return Backbone.Collection.extend({
    model: CategoryForecastEntry,
    url: '/api/v1/category_forecast_entries/'
  });
});