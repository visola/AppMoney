define(['underscore', 'view/BaseForm', 'tpl!template/transaction/edit.html', 'collection/Accounts', 'collection/Categories', 'model/Transaction'],
    function (_, BaseFormView, EditTemplate, Accounts, Categories, Transaction) {

  return BaseFormView.extend({
    template: EditTemplate,

    processData: function (data) {
      var value = data.value;

      value = value.replace(/,/g,'.'); // replace comma by dot
      value = Math.abs(parseFloat(value));

      if (!this.data.credit) {
        value = -1 * value;
      }

      data.value = value;
      data.toAccountId = this.data.account.id;
      return data;
    },

    initialize: function (toId, credit) {
      var _this = this,
        accounts = this.data.accounts = new Accounts(),
        categories = this.data.categories = new Categories();

      this.loading = true;

      this.data.credit = credit;
      this.model = new Transaction();

      Promise.all([accounts.fetch(), categories.fetch()]).then(function () {
        _this.loading = false;
        _this.data.account = accounts.get(toId);
        _this.render();
      });
    }
  });
});