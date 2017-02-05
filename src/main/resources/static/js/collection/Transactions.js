define(["backbone.paginator", 'jquery', 'model/Transaction'], function (PageableCollection, $, Transaction) {
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

  Transactions.betweenDates = function(start, end) {
    return new Promise((resolve, reject) => {
      $.ajax({
        url: '/api/v1/transactions/betweenDates',
        data: {start: start, end: end},
        error: reject,
        success: (data) => {
          resolve(new Transactions(data.map(d => new Transaction(d))));
        }
      });
    });
  };

  return Transactions;
});