define(['underscore', 'view/BaseForm', 'tpl!template/transaction/edit.html', 'collection/Accounts', 'collection/Categories', 'model/Transaction', 'router'],
    function (_, BaseFormView, EditTemplate, Accounts, Categories, Transaction, router) {

  return BaseFormView.extend({
    template: EditTemplate,
    events: {
      'click #delete-transaction' : 'deleteTransaction'
    },

    initialize: function (toId, creditOrFromAccountId) {
      var _this = this,
        accounts = this.data.accounts = new Accounts(),
        categories = this.data.categories = new Categories(),
        credit = typeof creditOrFromAccountId == 'boolean' ? creditOrFromAccountId : null,
        fromAccountId = typeof creditOrFromAccountId == 'string' ? creditOrFromAccountId : null;

      this.loading = true;
      this.data.fromAccount = null;
      categories.showHidden = true;

      if (this.model) {
        toId = this.model.get('toAccountId');
        if (this.model.get('fromAccountId') !== null) {
          fromAccountId = this.model.get('fromAccountId');
        } else {
          credit = this.model.get('value') >= 0;
        }
      } else {
        this.model = new Transaction();
      }

      this.data.credit = credit;
      Promise.all([accounts.fetch(), categories.fetch()]).then(function () {
        _this.loading = false;
        _this.data.account = accounts.get(toId);
        if (fromAccountId !== null) {
          _this.data.fromAccount = accounts.get(fromAccountId);
        }
        _this.render();
      });
    },

    deleteTransaction: function (e) {
      var confirmed = confirm("Você tem certeza que quer apagar esta transação?");

      if (confirmed === true) {
        this.model.destroy({
          wait:true,
          success: function () {
            router.navigate('/',{trigger:true});
          },
          error: function () {
            console.error(arguments);
            alert('Desculpe, aconteceu um erro enquanto tentávamos apagar a transação.');
          }
        });
      }
    },

    processData: function (data) {
      var value = data.value;

      value = value.replace(/,(\d+)$/g,'.$1'); // replace comma by dot
      value = Math.abs(parseFloat(value));

      if (this.data.credit === false) {
        value = -1 * value;
      }

      data.value = value;
      data.toAccountId = this.data.account.id;

      if (this.data.fromAccount) {
        data.fromAccountId = this.data.fromAccount.id;
      }

      return data;
    }
  });
});