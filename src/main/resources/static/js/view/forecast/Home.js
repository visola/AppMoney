define(['jquery', 'view/Base', 'bootstrap', 'collection/Categories', 'collection/CategoryForecastEntries', 'tpl!template/forecast/home.html'],
    function($, BaseView, Bootstrap, Categories, CategoryForecastEntries, ForecastHomeTemplate) {

  return BaseView.extend({
    template: ForecastHomeTemplate,
    events: {},

    initialize: function () {
      var _this = this;

      this.categories = new Categories();
      this.categoryEntries = new CategoryForecastEntries();
      this.loading = true;

      Promise.all([this.categories.fetch(), this.categoryEntries.fetch()])
      .then(function () {
        _this.loading = false;
        _this.render();
      });
    }
  });
});