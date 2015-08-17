define(["backbone", 'model/Permission'], function (Backbone, Permission) {
  var Permissions = Backbone.Collection.extend({
    model: Permission,
    initialize: function (accountId) {
      this.accountId = accountId;
    },
    url: function () {
      return '/api/v1/accounts/' + this.accountId + '/permissions';
    }
  });

  return Permissions;
});