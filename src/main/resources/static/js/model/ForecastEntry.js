define(["backbone", "moment"], function (Backbone, moment) {
  return Backbone.Model.extend({
    urlRoot: '/api/v1/forecast_entries/',
    defaults: {
      monthlyAmounts: [{
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear()
      }]
    },

    getAmountForDay: function(day, periodStartDay) {
      var i, start, end, nextAmount,
        monthlyAmounts = this.get('monthlyAmounts');
      for (i = 0; i < monthlyAmounts.length; i++) {
        start = moment([monthlyAmounts[i].year, monthlyAmounts[i].month - 1, periodStartDay]);
        nextAmount = monthlyAmounts[i + 1];
        end = nextAmount == null ? moment() : moment([nextAmount.year, nextAmount.month - 1, periodStartDay]);
        if (day.isBetween(start, end, null, "[)")) {
          return monthlyAmounts[i].amount;
        }
      }
      return 0;
    },

    getMinimumDate: function () {
      var monthlyAmounts = this.get('monthlyAmounts');
      monthlyAmounts.sort((a1, a2) => a1.year - a2.year || a1.month - a2.month);
      return moment([monthlyAmounts[0].year, monthlyAmounts[0].month - 1]);
    },

    matchesTransaction: function (transaction) {
      var forecastEntryId = transaction.get('forecastEntryId');
      if (forecastEntryId != null) {
        return forecastEntryId == this.get('id');
      }
      return this.get('categoryId') == transaction.get('categoryId');
    }
  });
});
