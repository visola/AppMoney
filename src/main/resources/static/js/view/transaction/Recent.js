define(['view/Base', 'view/PageControl', 'collection/Transactions', 'collection/Categories', 'collection/Accounts', 'tpl!/template/transaction/recent.html',],
    function (Base, PageControlView, Transactions, Categories, Accounts, RecentTransactionsTemplate) {

  var RecentTransactionsView = Base.extend({
    template: RecentTransactionsTemplate,

    initialize: function () {
      var transactions = this.collection = new Transactions(),
        categories = this.data.categories = new Categories(),
        accounts = this.data.accounts = new Accounts(),
        _this = this;
      this.loading = true;
      this.pageControlView = new PageControlView(transactions, this);

      Promise.all([transactions.fetch(), accounts.fetch(), categories.fetch()]).then(function () {
        _this.loading = false;
        _this.render();
      });
    },

    render: function () {
      RecentTransactionsView.__super__.render.apply(this, arguments);

      if (!this.loading) {
        this.pageControlView.setElement(this.$('#paging-before'));
        this.pageControlView.render();
      }
      return this;
    }
  });

  return RecentTransactionsView;
});