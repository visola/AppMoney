define(["backbone"], function (Backbone) {

  var Category = Backbone.Model.extend({
    urlRoot: '/api/v1/categories'
  });

  return Category;
});
