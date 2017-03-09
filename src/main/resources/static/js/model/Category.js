define(["backbone"], function (Backbone) {

  var Category = Backbone.Model.extend({
    urlRoot: '/api/v1/categories',

    getDepth: function () {
      var n = 0,
        parentId = this.get('parent') ? this.get('parent').id : null,
        parent = parentId != null ? this.collection.get(parentId) : null;

      while (parent != null) {
        n++;
        parentId = parent.get('parent') ? parent.get('parent').id : null;
        parent = parentId != null ? this.collection.get(parentId) : null;
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
      var parentId = this.get('parent') ? this.get('parent').id : null;
      if (parentId === null) {
        return this.get('name');
      } else {
        return this.collection.get(parentId).getLeafName() + ' | ' + this.get('name');
      }
    }
  });

  return Category;
});
