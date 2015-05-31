define(["backbone", "model/Account"], function (Backbone, Account) {
  var Accounts = Backbone.Collection.extend({
    model: Account,
    url: '/api/v1/accounts'
  });

  return Accounts;
});