define(["backbone"], function (Backbone) {
  var Permission = Backbone.Model.extend({
    initialize: function (accountId, userId) {
      this.accountId;
      this.userId;
    },
    url: function () {
      return '/api/v1/accounts/' + this.accountId + '/permissions' + this.userId;
    }
  });
  return Permission;
});
