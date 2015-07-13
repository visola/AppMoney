define(['underscore', 'router', 'view/BaseForm', 'tpl!template/account/edit.html', 'collection/Accounts', 'model/Account'],
    function (_, router, BaseFormView, EditTemplate, Accounts, Account) {

  return BaseFormView.extend({
    template: EditTemplate,
    events: {
      'click #delete-account' : 'deleteAccount'
    },

    deleteAccount: function (e) {
      var confirmed = confirm("Are you sure you want to delete this account?");

      if (confirmed === true) {
        this.model.destroy({
          wait:true,
          success: function () {
            router.navigate('/',{trigger:true});
          },
          error: function () {
            console.error(arguments);
            alert('Sorry, an error happened while deleting this account.');
          }
        });
      }
    },

    initialize: function (accountId) {
      var _this = this;
      this.loading = true;

      this.collection = new Accounts();
      this.model = new Account();

      this.collection.fetch().then(function () {
        _this.loading = false;

        if (accountId != 'new') {
          _this.model = _this.collection.get(accountId);
          _this.data.title = 'Edit Account';
        } else {
          _this.data.title = 'New Account';
        }

        _this.render();
      });
    },
  });
});