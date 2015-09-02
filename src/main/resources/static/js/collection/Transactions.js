define(["backbone.paginator", 'model/Transaction'], function (PageableCollection, Transaction) {
  var Transactions = PageableCollection.extend({
    url: '/api/v1/transactions',
    model: Transaction,

    parseRecords: function (data) {
      return data.content;
    },

    parseState: function (resp, queryParams, state, options) {
      return {
        currentPage: resp.number,
        totalPages: resp.totalPages,
        totalRecords: resp.totalElements
      };
    },

    queryParams: {
      pageSize: 'size'
    },

    state: {
      firstPage: 0,
      pageSize: 10
    }
  });

  return Transactions;
});