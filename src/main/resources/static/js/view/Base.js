define(['underscore', 'jquery', 'backbone', 'router'], function (_, $, Backbone, router) {
  // Base view idea inspired by: http://stackoverflow.com/questions/6968487/sub-class-a-backbone-view-sub-class-retain-events
  var BaseView = Backbone.View.extend({
    events: {
      'click a' : '__handleLink'
    },

    __handleLink : function (e) {
      var path;

      e.preventDefault();
      path = $(e.target).attr('href');
      router.navigate(path, {trigger:true});
    },

    render: function () {
      var text;
      if (this.loading) {
        text = "Loading...";
      } else {
        text = this.template(this);
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