define(['view/Base', 'tpl!template/permission/edit.html', 'collection/Permissions', 'model/Permission'],
    function (BaseView, EditPermission, Permissions, Permission) {

  return BaseView.extend({
    template: EditPermission,
    events: {
      'click .add': 'addUser'
    },

    initialize: function (account) {
      var _this = this;
      this.account = account;
      this.collection = new Permissions();
      this.loading = true;

      this.collection.fetch().then(function () {
        _this.loading = false;
        _this.render();
      });
    },

    addUser: function (e) {
      alert('test');
    },

    getModalOptions: function () {
      var _this = this;
      return {
        content: _this,
        title: "Account Permissions"
      };
    }
  });
});