define(["backbone", 'model/Category'], function (Backbone, Category) {
  var Categories = Backbone.Collection.extend({
    model: Category,
    initialize: function () {
      this.showHidden = false;
    },
    url: function () {
      var url = '/api/v1/categories';
      if (this.showHidden) {
        url += '?hidden=true';
      }
      return url;
    },
    save: function (options) {
      this.sync("update", this, options);
    }
  });

  return Categories;
});