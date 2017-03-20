define(['view/Base', 'view/PageControl', 'collection/Transactions', 'tpl!/template/transaction/recent.html'],
    function (Base, PageControlView, Transactions, RecentTransactionsTemplate) {

  var RecentTransactionsView = Base.extend({
    template: RecentTransactionsTemplate,

    initialize: function () {
      var transactions = this.collection = new Transactions();
      this.loading = true;
      this.pageControlView = new PageControlView(transactions, this);

      transactions.fetch().then(() => {
        this.loading = false;
        this.render();
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