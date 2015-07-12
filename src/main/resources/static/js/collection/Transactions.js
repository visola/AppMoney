define(["backbone", 'model/Transaction'], function (Backbone, Transaction) {
  var Transactions = Backbone.Collection.extend({
    url: '/api/v1/transactions',
    model: Transaction,

    parse: function (data) {
      return data.content;
    }
  });

  return Transactions;
});