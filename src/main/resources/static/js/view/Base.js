define(['underscore', 'jquery', 'backbone', 'router'], function (_, $, Backbone, router) {
  var BaseView = Backbone.View.extend({
    data: {},

    /**
     * By default, all links will 
     */
    events: {
      'click a:not[data-action]' : '__handleLink'
    },

    __handleLink : function (e) {
      var path;

      e.preventDefault();
      path = $(e.target).attr('href');
      router.navigate(path, {trigger:true});
    },

    prepareCollection: function () {
      var i,
        models = this.collection ? this.collection.models : [],
        result = [];

      for (i = 0; i < models.length; i++) {
        result.push(_.extend({}, models[i].attributes, this.processAttributes(models[i])));
      }

      return result;
    },

    prepareData: function () {
      return _.extend(this.data, {collection: this.prepareCollection(), model:this.prepareModel()});
    },

    prepareModel: function () {
      if (this.model) {
        return _.extend({}, this.model.attributes, this.processAttributes(this.model));
      }
    },

    processAttributes: function (model) {
      return {};
    },

    render: function () {
      var text;
      if (this.loading) {
        text = "Loading...";
      } else {
        text = this.template(this.prepareData());
      }
      this.$el.html(text);
      return this;
    }
  });

  BaseView.extend = function (child) {
    var view = Backbone.View.extend.apply(this, arguments);
    view.prototype.events = _.extend({}, this.prototype.events, child.events);
    return view;
  };

  return BaseView;
});