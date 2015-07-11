define(['view/Base', 'view/transaction/Recent', 'tpl!template/home.html', 'collection/Accounts'],
    function (BaseView, RecentTransactionsView, HomeTemplate, Accounts) {

  var HomeView = BaseView.extend({
    template: HomeTemplate,

    initialize: function () {
      var _this = this;
      this.loading = true;
      this.collection = new Accounts();
      this.recentTransactionsView = new RecentTransactionsView();

      this.collection.fetch().then(function () {
        _this.loading = false;
        _this.render();
      });
    },

    render: function () {
      HomeView.__super__.render.apply(this, arguments);

      if (!this.loading) {
        this.recentTransactionsView.render();
        this.$('#recentTransactions').append(this.recentTransactionsView.$el);
      }
      return this;
    }
  });

  return HomeView;
});