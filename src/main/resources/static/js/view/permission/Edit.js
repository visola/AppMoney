define(['underscore', 'view/Base', 'security', 'tpl!template/permission/edit.html', 'collection/AccountPermissions', 'model/UserPermissions'],
    function (_, BaseView, Security, EditPermission, AccountPermissions, UserPermissions) {

  return BaseView.extend({
    template: EditPermission,
    events: {
      'click .add': 'addUser',
      'click .permission': 'changePermission'
    },

    initialize: function (account) {
      var _this = this;

      this.bind('ok', this.handleOk);

      this.account = account;
      this.collection = new AccountPermissions(account.id);

      this.loading = true;
      this.collection.fetch().then(function () {
        _this.loading = false;
        _this.data.permissions = {};
        _this.data.userEmail = Security.getUserEmail();
        _this.render();
      });
    },

    addUser: function (e) {
      var userEmail = prompt('User email address:');
      if (userEmail) {
        this.collection.add(new UserPermissions({email: userEmail, accountId: this.account.id}));
        this.render();
      }
    },

    changePermission: function (e) {
      var $spanEl = this.$(e.target),
        tableRowId = $spanEl.parents('tr'),
        email = tableRowId.find('td:first').text(),
        model = this.collection.where({email:email})[0],
        permission = $spanEl.data('permission'),
        selected = $spanEl.data('value'),
        modelPermissions = model.get('permissions');

      if (selected == false && modelPermissions.indexOf(permission) < 0) {
        modelPermissions.push(permission);
      } else {
        modelPermissions = _.without(modelPermissions, permission);
      }

      model.set('permissions', modelPermissions.slice());
      this.render();
    },

    getModalOptions: function () {
      var _this = this;
      return {
        content: _this,
        title: "Account Permissions"
      };
    },

    handleOk: function () {
      this.collection.save();
    }
  });
});