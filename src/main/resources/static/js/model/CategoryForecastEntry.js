define(["backbone"], function (Backbone) {
  return Backbone.Model.extend({
    urlRoot: '/api/v1/category_forecast_entries/'
  });
});
