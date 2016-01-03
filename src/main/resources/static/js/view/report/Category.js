define(['jquery', 'view/Base', 'chart', 'moment', 'please', 'model/Reports', 'tpl!/template/report/category.html', 'tpl!/template/report/categoryLegend.html'],
    function ($, BaseView, Chart, moment, please, Reports, CategoryReportTemplate, LegendTemplate) {

  var CategoryReportView = BaseView.extend({
    template: CategoryReportTemplate,
    events: {
      "click .chart-legend a" : 'toggleCategory',
      "click .chart-legend a span" : 'toggleCategory'
    },

    initialize: function () {
      var _this = this;
      this.loading = true;
      this.data.exclude = [];
      this.data.startDate = moment();
      this.data.endDate = moment().subtract(30, 'days');

      Reports.totalPerCategory().then(function (response) {
        _this.loading = false;
        _this.render();

        _this.colors = null;
        _this.response = response;
        _this.drawChart(_this.filterAndProcessData());
      });
    },

    drawChart: function (data) {
      var i, j, segments,
        allData = data.data,
        chart = this.chart,
        context = this.$('#chart')[0].getContext('2d');

      if (chart != null) {
        this.chart.destroy();
      }

      chart = this.chart = new Chart(context).Pie(data.filtered, {
        animationSteps: 0,
        animateRotate: false,
        tooltipTemplate: '<%= label %>: $ <%= value.toFixed(2) %>'
      });
      segments = chart.segments;

      for (i = 0; i < segments.length; i++) {
        for (j = 0; j < allData.length; j++) {
          if (allData[j].label == segments[i].label) {
            allData[j] = segments[i];
            break;
          }
        }
      }

      this.$('#legend').html(LegendTemplate({
        exclude: this.data.exclude,
        segments: allData
      }));
    },

    filterAndProcessData: function () {
      var i,
        colors = this.colors,
        exclude = this.data.exclude,
        _this = this,
        total = 0,
        result = [],
        totalPerCategory = {};

      this.response
      .filter(function (row) {
        return moment(row).isBetween(_this.data.endDate, _this.data.startDate);
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

      result.sort(function (a, b) {
        return a.label.localeCompare(b.label);
      });

      if (!colors) {
        colors = this.colors = please.make_color({colors_returned: result.length, hue: 1, saturation: 0.5});
      }

      for (i = 0; i < result.length; i++) {
        total += result[i].value;
        result[i].color = colors[i];
      }

      return {
        data: result,
        filtered: result.filter(function (row) {return exclude.indexOf(row.label) < 0}),
        total: total
      };
    },

    toggleCategory: function (e) {
      var index,
        excluded = this.data.exclude,
        $clicked = $(e.target),
        $legendLink = $clicked[0].tagName == 'A' ? $clicked : $clicked.parent('a'),
        label = $legendLink.find('.legend-label').text();

      if ($legendLink.hasClass('exclude')) {
        index = excluded.indexOf(label);
        excluded.splice(index, 1);
        $legendLink.removeClass('exclude')
      } else {
        this.data.exclude.push(label);
        $legendLink.addClass('exclude')
      }

      this.drawChart(this.filterAndProcessData());
      e.preventDefault();
      e.stopPropagation();
    }

  });

  return CategoryReportView;
});