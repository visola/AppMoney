define(["backbone"], function (Backbone) {

  var Account = Backbone.Model.extend({
    defaults: {
      initialBalance: 0,
      initialBalanceDate: new Date(),
      type: "CHECKINGS"
    },

    urlRoot: '/api/v1/accounts',

    validate: function(attrs) {
      var invalid = [];

      if (attrs.name === "") {
        invalid.push("Hey dude! You must give a name for your account.");
      }

      if (isNaN(attrs.initialBalance) || attrs.initialBalance === "" || attrs.initialBalance == null) {
        invalid.push("Something is wrong with your balance.");
      }

      if (attrs.initialBalanceDate == null) {
        invalid.push("Please put a valid date.");
      } else {
        if (new Date(attrs.initialBalanceDate) > new Date()) {
          invalid.push("Hey McFly, you opened a account on future? Fix that please.");
        }
      }

      return invalid.length > 0 ? invalid : false;
    }
  });
  return Account;
});
