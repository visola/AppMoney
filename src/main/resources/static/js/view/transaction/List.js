define(['view/Base', 'tpl!/template/transaction/list.html'],
    function (BaseView, ListTransactionsTemplate) {

  return BaseView.extend({
    template: ListTransactionsTemplate,

    initialize: function (title, totalLine, categories, transactions) {
      this.categories = categories;
      this.title = title;
      this.transactions = transactions;
      this.totalLine = totalLine;
    },

    getModalOptions: function () {
      return {
        content: this,
        title: this.title
      };
    }
  });
});
