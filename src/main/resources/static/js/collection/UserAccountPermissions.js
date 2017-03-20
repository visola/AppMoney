define(["backbone", 'model/UserAccountPermission'], function (Backbone, UserAccountPermission) {
  var UserAccountPermissions = Backbone.Collection.extend({
    model: UserAccountPermission,
    url: '/api/v1/accounts/permissions'
  });

  return UserAccountPermissions;
});