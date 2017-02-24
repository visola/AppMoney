define(["chai", "moment", "model/ForecastEntry", "model/Transaction"], function (chai, moment, ForecastEntry, Transaction) {
  var forecastEntry,
    expect = chai.expect;

  beforeEach(function () {
    forecastEntry = new ForecastEntry({
      id: 1,
      categoryId: 1
    });
  });

  describe("model/ForecastEntry", function () {
    describe("get amount for day", function () {
      beforeEach(function () {
        forecastEntry.set('monthlyAmounts', [
          { month: 1, year: 2016, amount: 600 },
          { month: 1, year: 2017, amount: 800 }
        ]);
      });

      it("returns zero if outside period", function() {
        expect(
          forecastEntry.getAmountForDay(moment([2016,0,14]), 15)
        ).to.be.equal(0);
      });

      it("returns the right amount for the last day of the period", function() {
        expect(
          forecastEntry.getAmountForDay(moment([2017,0,14]), 15)
        ).to.be.equal(600);
      });

      it("returns the right amount for the first day of the period", function() {
        expect(
            forecastEntry.getAmountForDay(moment([2017,0,15]), 15)
          ).to.be.equal(800);
      });
    });

    describe("matches transaction", function () {
      it("matches a transaction that is mapped to it, even if it belongs to a different category", function () {
        var transaction = new Transaction({
          categoryId: 10,
          forecastEntryId: forecastEntry.get('id')
        });

        expect(forecastEntry.matchesTransaction(transaction)).to.be.eq(true);
      });

      it("matches a transaction when it's not mapped but has the same category ID", function () {
        var transaction = new Transaction({
          categoryId: 1,
          forecastEntryId: null
        });

        expect(forecastEntry.matchesTransaction(transaction)).to.be.eq(true);
      });

      it("doesn't match a transaction when it's not mapped and doesn't have the same category ID", function () {
        var transaction = new Transaction({
          categoryId: 10,
          forecastEntryId: 10
        });

        expect(forecastEntry.matchesTransaction(transaction)).to.be.eq(false);
      });

      it("doesn't match a transaction that's not mapped to it even if the same category", function () {
        var transaction = new Transaction({
          categoryId: 1,
          forecastEntryId: 10
        });

        expect(forecastEntry.matchesTransaction(transaction)).to.be.eq(false);
      });

      it("doesn't match a transaction that's not mapped to it and it's not the same category", function () {
        var transaction = new Transaction({
          categoryId: 10,
          forecastEntryId: 10
        });

        expect(forecastEntry.matchesTransaction(transaction)).to.be.eq(false);
      });
    });
  });

});
