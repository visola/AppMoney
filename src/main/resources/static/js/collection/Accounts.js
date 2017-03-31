define(["backbone", "jquery", "model/Account"], function (Backbone, $, Account) {
  var Accounts = Backbone.Collection.extend({
    model: Account,
    url: '/api/v1/accounts',
    balances: function () {
      return new Promise((resolve, reject) => {
        $.ajax({
          url: '/api/v1/accounts/balances',
          success: (data) => {
            let models = this.models;
            for (let i = 0; i < models.length; i++) {
              models[i].set('balance', data[models[i].get('id')]);
            }
            resolve(this);
          },
          error: function () {
            reject.apply(null, arguments);
          }
        });
      });
    }
  });

  return Accounts;
});