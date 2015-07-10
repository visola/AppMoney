define(['underscore', 'jquery', 'backbone', 'router'], function (_, $, Backbone, router) {
  var BaseView = Backbone.View.extend({
    data: {},

    /**
     * By default, all links will be handled as if they
     * wanted to take the router to where href points to.
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

    /**
     * Fetches data from all inputs in this view and return an object
     * mapping the input value to the element name attribute.
     * 
     * @parm formSelector (String) A selector to specify what form data
     *       should be loaded from. Defaults to <code>form</code>.
     */
    getFormData: function (formSelector) {
      var data = {};

      if (!formSelector) {
        formSelector = 'form';
      }

      this.$(formSelector).find('input, textarea, select').each(function (index, el) {
        var $el = $(el);
        data[$el.attr('name')] = $el.val();
      });

      return data;
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