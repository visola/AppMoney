define(["chai", "sinon", "moment", "big", "view/forecast/Home"], function (chai, sinon, moment, Big, ForecastHomeView) {
  var forecastEntry, homeView, server,
    expect = chai.expect;

  var categories = [{"id":1,"name":"Some Category","parentId":null,"hidden":false}];
  var forecast = {"id":1,"startDayOfMonth":15};
  var forecastEntries = [];
  var transactions = [];

  function createRequest(data) {
    return [200, { "Content-Type": "application/json" }, JSON.stringify(data)];
  }

  function checkAsync(callback) {
    return new Promise(function (resolve, reject) {
      setTimeout(() => {
        try {
          callback();
        } catch (e) {
          reject(e);
        }
        resolve();
      }, 0);
    });
  }

  beforeEach(function () {
    server = sinon.fakeServer.create({respondImmediately: true});
    server.respondWith("/api/v1/categories", createRequest(categories));
    server.respondWith("/api/v1/forecasts", createRequest(forecast));
    server.respondWith("/api/v1/forecast_entries/", createRequest(forecastEntries));
    server.respondWith(/.+betweenDates\?start=\d+&end=\d+/, createRequest(transactions));
  });

  afterEach(function () {
    server.restore()
  });

  describe("view/forecast/Home", function () {

    describe("get transactions", function () {
      it("returns the available transaction", function () {
        var transaction = {id: 10, toAccount: {id: 1}, fromAccount: null, happened: '2017-01-01', value: -100, category: categories[0]};
        server.respondWith(/.+betweenDates\?start=\d+&end=\d+/, createRequest([transaction]));

        homeView = new ForecastHomeView();
        return checkAsync( () => {
          expect(homeView.getTransactions().length).to.equal(1);
          expect(homeView.getTransactions()[0].get('id')).to.eq(transaction.id);
        });
      });

      it("filters transactions with a fromAccountId", function () {
        var transaction = {id: 10, toAccount: {id: 1}, fromAccount: {id: 2}, happened: '2017-01-01', value: -100, category: categories[0]};
        server.respondWith(/.+betweenDates\?start=\d+&end=\d+/, createRequest([transaction]));

        homeView = new ForecastHomeView();
        return checkAsync( () => {
          expect(homeView.getTransactions().length).to.equal(0);
        });
      });
    });

    describe("group by day", function () {
      var goBackInDays = 30,
        day = moment().subtract(goBackInDays, 'days').startOf('day'),
        days = [];

      for (var i = 0; i < goBackInDays; i++) {
        days.push(day);
        day = day.clone().add(1, 'day');
      }

      it("returns an array with the same length", function () {
        homeView = new ForecastHomeView();
        return checkAsync( () => {
          expect(homeView.groupByDay(days).length).to.equal(days.length);
        });
      });

      it("returns an array with all the days", function () {
        homeView = new ForecastHomeView();
        return checkAsync( () => {
          var i, groupedByDay = homeView.groupByDay(days);
          for (i = 0; i < groupedByDay.length; i++) {
            expect(groupedByDay[i].day.date()).to.equal(days[i].date());
          }
        });
      });

      context("when there are forecast entries", function () {
        var totalAmount = 600,
          day = moment([2017, 1, forecast.startDayOfMonth]),
          forecastEntry = {
            "id":7,
            "forecast":forecast,
            "title":"Some forecast entry",
            "category":categories[0],
            "monthlyAmounts":[{
              "month":1,
              "year":2016,
              "amount":totalAmount
            }]
          };

        it("contains the forecast item", function () {
          server.respondWith("/api/v1/forecast_entries/", createRequest([forecastEntry]));
          homeView = new ForecastHomeView();
          return checkAsync( () => {
            var groupedByDay = homeView.groupByDay([day]);
            expect(groupedByDay[0].entries.length).to.be.equal(1);
            expect(groupedByDay[0].entries[0].entry.get('id')).to.be.equal(forecastEntry.id);
          });
        });

        it("calculates the planned daily amount correctly for all days", function () {
          server.respondWith("/api/v1/forecast_entries/", createRequest([forecastEntry]));

          forecast = {"id":1,"startDayOfMonth":1};
          server.respondWith("/api/v1/forecasts", createRequest(forecast));

          homeView = new ForecastHomeView();
          return checkAsync( () => {
            var i, amount, groupedByDay,
              sum = Big(0),
              day = moment([2016, 0, 1]),
              dailyPerMonth = ['19.35', '20.69', '19.35', '20.00', '19.35', '20.00', '19.35', '19.35', '20.00', '19.35', '20.00', '19.35'],
              daysInPeriod = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

            for (i = 1; i < 366; i++) {
              day.dayOfYear(i);
              groupedByDay = homeView.groupByDay([day]);
              amount = groupedByDay[0].entries[0].amount;
              expect(amount.toFixed(2), `for day ${day.format('YYYY-MM-DD')}`).to.be.equal(dailyPerMonth[day.month()]);
              expect(amount.times(daysInPeriod[day.month()], `total for day ${day.format('YYYY-MM-DD')}`).toFixed(0)).to.be.equal(totalAmount+'');
              sum = sum.plus(amount);
              if (day.date() == day.clone().endOf('month').date()) {
                expect(sum.toFixed(0), `summed amount for month ${day.month()}`).to.be.equal(totalAmount+'');
                sum = Big(0);
              }
            }
          });
        });
      });

      context("when there are transactions", function () {
        var totalAmount = 600;

        it("contains the transaction item", function () {
          var day = moment(),
            transaction = {
              id: 10,
              toAccount: {id: 1},
              fromAccount: null,
              happened: day.format("YYYY-MM-DD"),
              value: -100,
              category: categories[0]
          };
          server.respondWith(/.+betweenDates\?start=\d+&end=\d+/, createRequest([transaction]));

          homeView = new ForecastHomeView();
          return checkAsync( () => {
            groupedByDay = homeView.groupByDay([day]);
            expect(groupedByDay[0].day.format("YYYY-MM-DD")).to.be.equal(day.format("YYYY-MM-DD"));
            expect(groupedByDay[0].transactions.length).to.be.equal(1);
            expect(groupedByDay[0].transactions[0].id).to.be.equal(transaction.id);
            expect(groupedByDay[0].transactions[0].get("value")).to.be.equal(transaction.value);
          });
        });
      });

    });
  });
});
