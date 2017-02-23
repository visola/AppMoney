define(["chai", "moment", "model/ForecastEntry"], function (chai, moment, ForecastEntry) {
  var forecastEntry,
    expect = chai.expect;

  beforeEach(function () {
    forecastEntry = new ForecastEntry();
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
  });

});
