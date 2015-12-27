define(["backbone"], function (Backbone) {

  var Category = Backbone.Model.extend({
    urlRoot: '/api/v1/categories',
    getLeafName: function () {
      var parentId = this.get('parentId');
      if (parentId === null) {
        return this.get('name');
      } else {
        return this.collection.get(parentId).getLeafName() + ' | ' + this.get('name');
      }
    }
  });

  return Category;
});
