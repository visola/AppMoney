define(["backbone"], function (Backbone) {

  var Transaction = Backbone.Model.extend({
    urlRoot: '/api/v1/transactions'
  });
  return Transaction;
});
