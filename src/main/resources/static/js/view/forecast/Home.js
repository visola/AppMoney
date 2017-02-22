define([
  'jquery',
  'view/Base',
  'bootstrap',
  'big',
  'chart2',
  'moment',
  'collection/Categories',
  'collection/ForecastEntries',
  'collection/Transactions',
  'model/Forecast',
  'tpl!template/forecast/home.html'
], function(
  $,
  BaseView,
  Bootstrap,
  Big,
  Chart,
  moment,
  Categories,
  ForecastEntries,
  Transactions,
  Forecast,
  ForecastHomeTemplate
) {

  return BaseView.extend({
    template: ForecastHomeTemplate,
    events: {},

    initialize: function () {
      this.interval = 60;
      this.totalPeriod = this.interval + 31;

      this.visibleDays = this.getTimelineDays(this.interval);
      this.totalDays = this.getTimelineDays(this.totalPeriod);

      this.categories = new Categories();
      this.entries = new ForecastEntries();
      this.forecast = new Forecast();

      this.recalculate();
    },

    cleanNumber: function (number) {
      return Math.round(number * 100) / 100;
    },

    drawTimeline: function () {
      var chart,
        ctx = $('#timeline');
      if (ctx.length === 0) {
        return;
      }

      chart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: this.visibleDays.map(d => d.date()),
          datasets: [{
            backgroundColor: 'rgba(150, 200, 150, 0.2)',
            borderColor: 'rgba(200, 255, 200, 1)',
            data: this.getPlannedTimelineData(),
            label: 'Planejado',
            lineTension: 0
          },{
            backgroundColor: 'rgba(200, 150, 150, 0.2)',
            borderColor: 'rgba(255, 200, 200, 1)',
            data: this.getSpentTimelineData(),
            label: 'Realizado',
            lineTension: 0
          }]
        }
      });
    },

    getCategoriesInForecast: function () {
      return this.entries.map(e => e.get('categoryId'));
    },

    getEntriesForPeriod: function (offset) {
      var d, periodStart,
        startDay = this.forecast.get('startDayOfMonth'),
        periodEnd = moment().date(startDay),
        result = [],
        groupedByDay = this.groupedByDay;

      if (moment().isSameOrAfter(periodEnd)) {
        periodEnd = periodEnd.add(1, 'month');
      }

      periodEnd = periodEnd.add(offset, 'month');
      periodStart = periodEnd.clone().subtract(1, 'month');

      for (d = 0; d < groupedByDay.length; d++) {
        if (groupedByDay[d].day.isBetween(periodStart, periodEnd, 'day', '[)')) {
          result.push(groupedByDay[d]);
        }
      }

      return result;
    },

    getPlannedTimelineData: function () {
      return this.getTotalForDays(
          g => g.entries,
          e => e.amount
      );
    },

    getPlannedTotalForEntryAndPeriodOffset: function (entryId, periodOffset) {
      var groupedEntries = this.getEntriesForPeriod(periodOffset);
      return groupedEntries.reduce( (total, ge) => {
        return total.plus(ge.entries
          .filter( e => e.entry.get('id') == entryId )
          .reduce( (subTotal, e) => subTotal.plus(e.amount), Big(0)));
      }, Big(0));
    },

    getSpentTimelineData: function () {
      return this.getTotalForDays(
          g => g.transactions,
          t => Big(t.get('value')).abs()
      );
    },

    getSpentTotalForEntryAndPeriodOffset: function (entryId, periodOffset) {
      var forecastEntry = entryId == -1 ? null : this.entries.get(entryId),
        groupedEntries = this.getEntriesForPeriod(periodOffset);
      return groupedEntries.reduce( (total, ge) => {
        return total.plus(ge.transactions
          .filter( t => {
            if (forecastEntry == null) {
              // Find transactions that doesn't match any entry
              return ge.entries
                .filter( (e) => !e.entry.matchesTransaction(t) )
                .length > 0;
            } else {
              // Find the transactions that matches the entry
              return forecastEntry.matchesTransaction(t);
            }
          })
          .reduce( (subTotal, t) => subTotal.plus(Big(t.get('value')).abs()), Big(0)));
      }, Big(0));
    },

    getTimelineDays: function (interval) {
      var d,
        day = moment().subtract(interval, 'd'),
        result = [];
      for (d = 1; d <= interval; d++) {
        result.push(day.clone());
        day.add(1, 'd');
      }
      return result;
    },

    getTotalForDays: function (arrayForDay, getValue) {
      var d,
        y = Big(0),
        visibleDays = this.visibleDays,
        totalDays = this.totalDays,
        startDay = this.forecast.get('startDayOfMonth'),
        groupedByDay = this.groupedByDay,
        result = [];

      // Calculate initial value before first visible day
      for (d = 0; d < groupedByDay.length - visibleDays.length; d++) {
        day = groupedByDay[d].day;
        if (day.date() == startDay) {
          y = Big(0);
        }
        y = y.plus(arrayForDay(groupedByDay[d]).reduce((total, e) => total.plus(getValue(e)), Big(0)));
      }

      // Add total
      for (d = groupedByDay.length - visibleDays.length; d < groupedByDay.length; d++) {
        day = groupedByDay[d].day;
        if (day.date() == startDay) {
          y = Big(0);
        }
        y = y.plus(arrayForDay(groupedByDay[d]).reduce((total, e) => total.plus(getValue(e)), Big(0)));
        result.push(y.toFixed(2));
      }
      return result;
    },

    getTransactions: function () {
      return this.transactions.filter((t) => {
        return t.get('fromAccountId') === null && t.get('value') < 0;
      });
    },

    groupByDay: function (days) {
      var i, day, temp, eachDay, entryDay, daysInPeriod, amount,
        currentPeriodStart, previousPeriodStart, daysBetweenPeriods,
        startDay = this.forecast.get('startDayOfMonth'),
        byDay = [];
      for (i = 0; i < days.length; i++) {
        day = days[i];
        currentPeriodStart = day.clone();
        currentPeriodStart.date(startDay);

        previousPeriodStart = currentPeriodStart.clone();
        if (day.isBefore(currentPeriodStart)) {
          previousPeriodStart.subtract(1, 'month');
        } else {
          previousPeriodStart.add(1, 'month');
          temp = currentPeriodStart;
          currentPeriodStart = previousPeriodStart;
          previousPeriodStart = temp;
        }

        daysBetweenPeriods = currentPeriodStart.diff(previousPeriodStart, 'days');
        eachDay = {day: day, entries: [], transactions: []};
        byDay.push(eachDay);
        this.entries.forEach(e => {
          if (day.isSameOrAfter(e.getMinimumDate())) {
            amount = new Big(e.getAmountForDay(day, startDay));
            eachDay.entries.push({
              amount: amount.div(daysBetweenPeriods),
              entry: e
            });
          }
        });

        this.getTransactions().forEach(t => {
          var tDay = moment(t.get('happened'));
          if (tDay.isSame(day, 'day')) {
            eachDay.transactions.push(t);
          }
        });
      }
      return byDay;
    },

    postAppend: function () {
      this.drawTimeline();
    },

    recalculate: function () {
      this.loading = true;

      var transactionsPromise = Transactions
        .betweenDates(moment().subtract(this.totalPeriod, 'd').unix() * 1000, moment().unix() * 1000);

      Promise
      .all([this.categories.fetch(), this.entries.fetch(), this.forecast.fetch(), transactionsPromise])
      .then(data => {
        this.transactions = data[3];
        this.loading = false;

        this.groupedByDay = this.groupByDay(this.totalDays);
        this.render();
      });
    }
  });
});
