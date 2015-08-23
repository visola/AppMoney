define(["backbone", 'model/UserPermissions'], function (Backbone, UserPermissions) {
  var AccountPermissions = Backbone.Collection.extend({
    model: UserPermissions,
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