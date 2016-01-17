define(["backbone"], function (Backbone) {

  var Category = Backbone.Model.extend({
    urlRoot: '/api/v1/categories',

    getDepth: function () {
      var n = 0,
        parent = this.collection.get(this.get('parentId'));

      while (parent != null) {
        n++;
        parent = this.collection.get(parent.get('parentId'));
      }

      return n;
    },

    getHierarchicalRepresentation: function (space) {
      var i,
        depth = this.getDepth(),
        result = '';

      for (i = 0; i < depth; i++) {
        result += space;
      }
      return result + this.get('name');
    },

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
