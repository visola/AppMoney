define(["backbone", 'model/Category'], function (Backbone, Category) {
  var Categories = Backbone.Collection.extend({
    url: '/api/v1/categories',
    model: Category
  });

  return Categories;
});