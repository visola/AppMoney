define(['view/Base', 'tpl!template/home.html', 'collection/Accounts'], function (BaseView, HomeTemplate, Accounts) {
  return BaseView.extend({
    template: HomeTemplate,

    initialize: function () {
      var _this = this;
      this.loading = true;
      this.collection = new Accounts();

      this.collection.fetch().then(function () {
        _this.loading = false;
        _this.render();
      });
    },
  });
});