define(['underscore', 'view/BaseForm', 'tpl!template/account/edit.html', 'collection/Accounts', 'model/Account'],
    function (_, BaseFormView, EditTemplate, Accounts, Account) {

  return BaseFormView.extend({
    template: EditTemplate,

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