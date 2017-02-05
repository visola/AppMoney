define([
  'jquery',
  'view/Base',
  'bootstrap',
  'chart2',
  'moment',
  'collection/Categories',
  'collection/CategoryForecastEntries',
  'collection/Transactions',
  'model/Forecast',
  'tpl!template/forecast/home.html'
], function(
  $,
  BaseView,
  Bootstrap,
  Chart,
  moment,
  Categories,
  CategoryForecastEntries,
  Transactions,
  Forecast,
  ForecastHomeTemplate
) {

  return BaseView.extend({
    template: ForecastHomeTemplate,
    events: {},

    initialize: function () {
      var _this = this;

      this.interval = 30;
      this.days = this.getTimelineDays(this.interval);

      this.categories = new Categories();
      this.categoryEntries = new CategoryForecastEntries();
      this.forecast = new Forecast();
      this.loading = true;

      var transactionsPromise = Transactions
        .betweenDates(moment().subtract(60, 'd').unix() * 1000, moment().unix() * 1000);

      Promise
      .all([this.categories.fetch(), this.categoryEntries.fetch(), this.forecast.fetch(), transactionsPromise])
      .then(function (data) {
        _this.transactions = data[3];
        _this.loading = false;
        _this.calculateTotals();
        _this.render();
        _this.drawTimeline();
      });
    },

    calculateTotalPerCategory: function (start, end) {
      var i,
        result = {},
        categoriesInForecast = this.getCategoriesInForecast(),
        transactions = this.transactions;

      transactions.each((t) => {
        var subtotal,
          category = t.get('categoryId'),
          happened = t.get('happened'),
          value = t.get('value');

        if (categoriesInForecast.indexOf(category) < 0) {
          category = 'other';
        }
        if (start.isSameOrBefore(happened, 'day') &&
            end.isSameOrAfter(happened, 'day')) {
          subtotal = result[category] || 0;
          subtotal += value;
          result[category] = subtotal;
        }
      });
      return result;
    },

    calculateTotals: function () {
      var startPast, endPast,
        endPresent = moment(),
        startPresent = moment().date(this.forecast.get('startDayOfMonth'));

      if (startPresent.isAfter(endPresent)) {
        startPresent.subtract(1, 'months');
      }

      startPast = startPresent.clone().subtract(1, 'months'),
      endPast = startPresent.clone().subtract(1, 'days');

      this.totalPerCategoryPresent = this.calculateTotalPerCategory(startPresent, endPresent);
      this.totalPerCategoryPast = this.calculateTotalPerCategory(startPast, endPast);
    },

    drawTimeline: function () {
      var chart,
        days = this.days,
        ctx = $('#timeline');
      if (ctx.length === 0) {
        return;
      }

      chart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: days,
          datasets: [{
            backgroundColor: 'rgba(150, 200, 150, 0.2)',
            borderColor: 'rgba(200, 255, 200, 1)',
            data: this.getPlannedTimelineData(days),
            label: 'Planejado',
            lineTension: 0
          },{
            backgroundColor: 'rgba(200, 150, 150, 0.2)',
            borderColor: 'rgba(255, 200, 200, 1)',
            data: this.getSpentTimelineData(days).reverse(),
            label: 'Realizado',
            lineTension: 0
          }]
        }
      });
    },

    getCategoriesInForecast: function () {
      return this.categoryEntries.map(e => e.get('categoryId'));
    },

    getPlannedTimelineData: function (days) {
      var d, i,
        y = 0,
        startDay = this.forecast.get('startDayOfMonth'),
        firstDay = days[0],
        entries = this.categoryEntries,
        result = [];

      entries.each(entry => y += entry.get('amount') * (firstDay + startDay) / 30);

      for (d = 0; d < days.length; d++) {
        if (days[d] == startDay) {
          y = 0;
        }
        for (i = 0; i < entries.length; i++) {
          y += entries.at(i).get('amount') / 30;
        }
        result.push(y);
      }
      return result;
    },

    getSpentTimelineData: function (days) {
      var d,
        y = 0,
        result = [],
        firstDay = days[0],
        startDay = this.forecast.get('startDayOfMonth'),
        transactions = this.transactions,
        startGraph = moment().subtract(this.interval, 'd'),
        previousStart = this.getPreviousStart();
      transactions.each((t) => {
        var happened = t.get('happened'),
          value = t.get('value');
        if (previousStart.isBefore(happened)
            && startGraph.isAfter(happened)
            && value < 0) {
          y += Math.abs(value);
        }
      });
      for (d = 0; d < days.length; d++) {
        if (days[d] == startDay) {
          y = 0;
        }

        transactions.each((t) => {
          var happened = t.get('happened'),
            value = t.get('value');
          if (startGraph.isSame(happened, 'day') && value < 0) {
            y += Math.abs(value);
          }
        });;
        result.push(y);
        startGraph.add(1, 'd');
      }
      return result.reverse();
    },

    getTimelineDays: function (interval) {
      var d,
        day = moment().subtract(interval, 'd'),
        result = [];
      for (d = 1; d <= interval; d++) {
        result.push(day.date());
        day.add(1, 'd');
      }
      return result;
    },

    getPreviousStart: function () {
      var day = moment().subtract(this.interval, 'd'),
        startDay = this.forecast.get('startDayOfMonth');
      while (day.date() != startDay) {
        day.subtract(1, 'd');
      }
      return day;
    }
  });
});