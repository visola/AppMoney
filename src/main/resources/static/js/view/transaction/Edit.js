define(['underscore', 'moment', 'view/Base', 'tpl!template/transaction/edit.html', 'collection/Accounts', 'collection/Categories', 'model/Transaction'],
    function (_, Moment, BaseView, EditTemplate, Accounts, Categories, Transaction) {

  return BaseView.extend({
    template: EditTemplate,
    events: {
      'submit form' : 'handleSave'
    },

    handleSave: function (e) {
      var data = this.getFormData(),
        value = data.value;

      e.preventDefault();

      value = value.replace(/,/g,'.'); // replace comma by dot
      value = Math.abs(parseFloat(value));

      if (!this.data.credit) {
        value = -1 * value;
      }
      data.value = value;
      data.toAccountId = this.data.account.id;

      this.model.save(data, {
        wait:true,
        success: function() {
          alert("Data saved successfully!");
        },
        error: function () {
          console.log(arguments);
          alert("Sorry, an error happend. Please try again later.");
        }
      });
    },

    initialize: function (toId, credit) {
      var _this = this,
        accounts = this.data.accounts = new Accounts(),
        categories = this.data.categories = new Categories();

      this.loading = true;

      this.data.Moment = Moment;
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