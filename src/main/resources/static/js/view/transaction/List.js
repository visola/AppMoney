define(['view/Base', 'tpl!/template/transaction/list.html'],
    function (BaseView, ListTransactionsTemplate) {

  return BaseView.extend({
    template: ListTransactionsTemplate,
    events: {
      'click a' : 'hide'
    },

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
    },

    hide: function () {
      this.$el.parents(".modal").modal("hide");
    }
  });
});
