define(["backbone", 'model/Category'], function (Backbone, Category) {
  var Categories = Backbone.Collection.extend({
    model: Category,

    comparator: function (c1, c2) {
      return c1.getLeafName().localeCompare(c2.getLeafName());
    },

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
      return this.sync("update", this, options);
    }
  });

  return Categories;
});