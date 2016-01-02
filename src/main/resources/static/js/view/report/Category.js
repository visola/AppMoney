define(['jquery', 'view/Base', 'chart', 'moment', 'model/Reports', 'tpl!/template/report/category.html', 'text!/template/report/categoryLegend.html'],
    function ($, BaseView, Chart, moment, Reports, CategoryReportTemplate, legendTemplate) {

  var CategoryReportView = BaseView.extend({
    template: CategoryReportTemplate,
    events: {
      "click .chart-legend > a" : 'toggleCategory'
    },

    initialize: function () {
      var _this = this;
      this.loading = true;

      Reports.totalPerCategory().then(function (response) {
        _this.loading = false;
        _this.render();

        _this.drawChart(_this.filterAndProcessData(response));
      });
    },

    drawChart: function (data) {
      var chart,
        context = this.context || this.$('#chart')[0].getContext('2d');

      chart = new Chart(context).Pie(data, {legendTemplate : legendTemplate});
      this.$el.append($(chart.generateLegend()));
    },

    filterAndProcessData: function (response) {
      var i,
        dateEnd = moment(),
        dateStart = moment().subtract(30, 'days'),
        result = [],
        totalPerCategory = {};

      response
      .filter(function (row) {
        return moment(row).isBetween(dateStart, dateEnd);
      }).forEach(function (row) {
        var total = totalPerCategory[row.category] || 0;
        totalPerCategory[row.category] = total + row.total;
      });

      for (var label in totalPerCategory) {
        result.push({
          label: label,
          value: totalPerCategory[label]
        });
      }

      return result;
    },

    toggleCategory: function (e) {
      var $legendLink = $(e.target),
        label = $legendLink.find('.legend-label').text();

      e.preventDefault();
    }

  });

  return CategoryReportView;
});