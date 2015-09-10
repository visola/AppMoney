define(["backbone"], function (Backbone) {

  var Transaction = Backbone.Model.extend({
    defaults: {
      happened: new Date()
    },
    urlRoot: '/api/v1/transactions'
  });
  return Transaction;
});
