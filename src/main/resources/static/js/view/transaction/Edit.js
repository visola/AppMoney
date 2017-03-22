define([
  'underscore',
  'view/BaseForm',
  'tpl!template/transaction/edit.html',
  'collection/Accounts',
  'collection/Categories',
  'collection/ForecastEntries',
  'model/Transaction',
  'router'
], function (
  _,
  BaseFormView,
  EditTemplate,
  Accounts,
  Categories,
  ForecastEntries,
  Transaction,
  router
) {

  return BaseFormView.extend({
    template: EditTemplate,
    events: {
      'click #delete-transaction' : 'deleteTransaction'
    },

    initialize: function (toId, creditOrFromAccountId) {
      var accounts = this.data.accounts = new Accounts(),
        categories = this.data.categories = new Categories(),
        forecastEntries = this.data.forecastEntries = new ForecastEntries(),
        credit = typeof creditOrFromAccountId == 'boolean' ? creditOrFromAccountId : null,
        fromAccountId = typeof creditOrFromAccountId == 'string' ? creditOrFromAccountId : null;

      this.loading = true;
      this.data.fromAccount = null;

      if (this.model) {
        toId = this.model.get('toAccount').id;
        if (this.model.get('fromAccount') !== null) {
          fromAccountId = this.model.get('fromAccount').id;
        } else {
          credit = this.model.get('value') >= 0;
        }
      } else {
        this.model = new Transaction();
      }

      this.data.credit = credit;
      Promise.all([accounts.fetch(), categories.fetch(), forecastEntries.fetch()]).then(() => {
        this.loading = false;
        this.data.account = accounts.get(toId);
        if (fromAccountId !== null) {
          this.data.fromAccount = accounts.get(fromAccountId);
        }
        this.render();
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

      data.category = {id: data.categoryId};
      if (data.forecastEntryId == "") {
        data._to_remove = ['forecastEntry', 'forecastEntryId'];
        delete data.forecastEntryId;
      } else {
        data.forecastEntry = {id: data.forecastEntryId};
      }

      data.value = value;
      data.toAccount = {id: this.data.account.id};

      if (this.data.fromAccount) {
        data.fromAccount = {id: this.data.fromAccount.id};
      }

      return data;
    }
  });
});
