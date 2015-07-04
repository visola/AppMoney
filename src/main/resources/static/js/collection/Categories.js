define(["backbone"], function (Backbone) {
  var Categories = Backbone.Collection.extend({
    url: '/api/v1/categories'
  });

  return Categories;
});