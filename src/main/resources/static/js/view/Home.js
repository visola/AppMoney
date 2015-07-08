define(['view/Base', 'tpl!template/home.html', 'collection/Accounts'], function (BaseView, HomeTemplate, Accounts) {
  return BaseView.extend({
    template: HomeTemplate,

    initialize: function () {
      var _this = this,
        accounts = new Accounts();

      this.loading = true;

      accounts.fetch().then(function () {
        _this.loading = false;
        _this.accounts = accounts.models;
        _this.render();
      });
    },
  });
});