define(['underscore', 'moment', 'view/Base', 'tpl!template/account/edit.html', 'collection/Accounts', 'model/Account'],
    function (_, Moment, BaseView, EditTemplate, Accounts, Account) {

  return BaseView.extend({
    template: EditTemplate,

    initialize: function (accountId) {
      var _this = this;
      this.loading = true;

      this.collection = new Accounts();
      this.model = new Account();

      this.collection.fetch().then(function () {
        _this.loading = false;

        if (accountId) {
          _this.model = _this.collection.get(accountId);
          _this.data.title = 'Edit Account';
        } else {
          _this.model = new Account();
          _this.data.title = 'New Account';
        }

        _this.render();
      });
    },

    processAttributes: function (model) {
      return {
        initialBalanceDate: Moment(model.get('initialBalanceDate')).format('YYYY-MM-DD')
      };
    }
  });
});