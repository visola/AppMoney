define(["backbone", 'model/UserPermissions'], function (Backbone, UserPermissions) {
  var Permissions = Backbone.Collection.extend({
    model: UserPermissions,
    initialize: function (accountId) {
      this.accountId = accountId;
    },
    url: function () {
      return '/api/v1/accounts/' + this.accountId + '/permissions';
    }
  });

  return Permissions;
});