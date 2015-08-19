define(["backbone"], function (Backbone) {
  var UserPermissions = Backbone.Model.extend({
    initialize: function (accountId, userId) {
      this.accountId;
      this.userId;
    },
    hasPermission: function (permission) {
      return this.get('permissions').indexOf(permission) >= 0;
    },
    url: function () {
      return '/api/v1/accounts/' + this.accountId + '/permissions' + this.userId;
    }
  });
  return UserPermissions;
});
