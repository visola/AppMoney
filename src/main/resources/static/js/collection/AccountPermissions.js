define(["backbone", 'model/UserAccountPermission'], function (Backbone, UserAccountPermission) {
  var AccountPermissions = Backbone.Collection.extend({
    model: UserAccountPermission,
    initialize: function (accountId) {
      this.accountId = accountId;
    },
    save: function (options) {
      this.sync("update", this, options);
    },
    url: function () {
      return '/api/v1/accounts/' + this.accountId + '/permissions';
    }
  });

  return AccountPermissions;
});