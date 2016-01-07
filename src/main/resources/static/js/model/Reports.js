define(['jquery'], function ($) {

  return {
    totalPerCategory: function () {
      return $.get("/api/v1/reports/totals/per-category");
    }
  };
});