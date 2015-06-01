define(["backbone"], function (Backbone) {
  var Account = Backbone.Model.extend({
    url: '/api/v1/accounts'
  });

  return Account;
});