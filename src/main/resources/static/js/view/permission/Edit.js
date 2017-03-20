define(['underscore', 'view/Base', 'security', 'tpl!template/permission/edit.html', 'collection/AccountPermissions', 'model/UserAccountPermission'],
    function (_, BaseView, Security, EditPermission, AccountPermissions, UserAccountPermission) {

  return BaseView.extend({
    template: EditPermission,
    events: {
      'click .add': 'addUser',
      'click .permission': 'changePermission'
    },

    initialize: function (account) {
      this.bind('ok', this.handleOk);

      this.account = account;
      this.collection = new AccountPermissions(account.id);

      this.loading = true;
      this.collection.fetch().then(() => {
        this.loading = false;
        this.data.userEmail = Security.getUserEmail();
        this.data.userEmails = {};
        this.collection.models
          .map(p => p.get('user'))
          .forEach(u => this.data.userEmails[u.username] = {id: u.id, username: u.username});
        this.render();
      });
    },

    addUser: function (e) {
      var username = prompt('Email do usuário:');
      if (username) {
        this.data.userEmails[username] = {username: username}
        this.render();
      }
    },

    changePermission: function (e) {
      var $spanEl = this.$(e.target),
        tableRowId = $spanEl.parents('tr'),
        username = tableRowId.find('td:first').text(),
        permission = $spanEl.data('permission'),
        selected = $spanEl.data('value'),
        model = this.collection.filter(p => username == p.get('user').username && permission == p.get('permission'))[0];

      if (selected === true) {
        // remove permission from collection
        this.collection.remove(model);
      } else {
        if (model == null) {
          // add it to the collection
          this.collection.add(
              new UserAccountPermission(
                  {
                    permission: permission,
                    user: this.data.userEmails[username],
                    account: {id: this.account.id}
                  }
              )
          );
        }
      }
      this.render();
    },

    getModalOptions: function () {
      var _this = this;
      return {
        content: _this,
        title: "Permissões da Conta"
      };
    },

    handleOk: function () {
      this.collection.save();
    }
  });
});