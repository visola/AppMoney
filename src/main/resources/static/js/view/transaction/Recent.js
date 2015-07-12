define(['view/Base', 'collection/Transactions', 'collection/Categories', 'collection/Accounts', 'tpl!/template/transaction/recent.html',],
    function (Base, Transactions, Categories, Accounts, RecentTransactionsTemplate) {

  var RecentTransactionsView = Base.extend({
    template: RecentTransactionsTemplate,
    initialize: function () {
      var transactions = this.collection = new Transactions(),
        categories = this.data.categories = new Categories(),
        accounts = this.data.accounts = new Accounts(),
        _this = this;
      this.loading = true;

      Promise.all([transactions.fetch(), accounts.fetch(), categories.fetch()]).then(function () {
        _this.loading = false;
        _this.render();
      });
    }
  });

  return RecentTransactionsView;
});