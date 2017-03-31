define(['view/Base', 'view/transaction/Recent', 'tpl!template/home.html', 'collection/Accounts', 'collection/UserAccountPermissions'],
    function (BaseView, RecentTransactionsView, HomeTemplate, Accounts, UserAccountPermissions) {

  var HomeView = BaseView.extend({
    template: HomeTemplate,
    events: {
      'click .transferTo' : 'transferTo'
    },

    hasPermission: function (accountId, permissionsToCheck) {
      let permissions = this.permissions;
      for (let i = 0; i < permissions.length; i++) {
        let permission = permissions.at(i);
        if (permission.get('account').id == accountId
            && permissionsToCheck.indexOf(permission.get('permission')) >= 0) {
          return true;
        }
      }
      return false;
    },

    initialize: function () {
      this.loading = true;
      this.collection = new Accounts();
      this.permissions = new UserAccountPermissions();
      this.recentTransactionsView = new RecentTransactionsView();

      Promise.all([this.collection.fetch(), this.permissions.fetch()]).then(() => {
        this.loading = false;
        this.collection.balances().then( () => this.render() );
        this.render();
      });
    },

    render: function () {
      HomeView.__super__.render.apply(this, arguments);

      if (!this.loading) {
        this.recentTransactionsView.render();
        this.$('#recentTransactions').append(this.recentTransactionsView.$el);
      }
      return this;
    },

    transferTo: function (e) {
      var $button = this.$(e.target),
        toAccountId = $button.data('to');
      e.preventDefault();
      this.toAccountId = toAccountId;
      this.render();
    }
  });

  return HomeView;
});