define(['underscore', 'moment', 'view/Base', 'tpl!template/account/edit.html', 'collection/Accounts', 'model/Account'],
    function (_, Moment, BaseView, EditTemplate, Accounts, Account) {

  return BaseView.extend({
    template: EditTemplate,
    events: {
      'submit form' : 'handleSave'
    },

    handleSave: function (e) {
      e.preventDefault();
      this.model.save(this.getFormData(), {
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

    processAttributes: function (model) {
      return {
        initialBalanceDate: Moment(model.get('initialBalanceDate')).format('YYYY-MM-DD')
      };
    }
  });
});