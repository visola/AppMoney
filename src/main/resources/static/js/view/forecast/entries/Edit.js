define([
        'underscore',
        'view/BaseForm',
        'tpl!template/forecast/entries/edit.html',
        'collection/ForecastEntries',
        'collection/Categories',
        'model/ForecastEntry',
        'router'],
    function (
        _,
        BaseFormView,
        EditTemplate,
        ForecastEntries,
        Categories,
        ForecastEntry,
        router) {

  return BaseFormView.extend({
    template: EditTemplate,

    events: {
      "click button[type=submit]": "__handleSave",

      "click #addMonthlyAmount": "addMonthlyAmount",
      "click .removeMonthlyAmount": "removeMonthlyAmount",

      "change table.monthly input,select": "monthlyAmountsChanged",
      "keyup table.monthly input": "monthlyAmountsChanged"
    },

    initialize: function (entryId) {
      var _this = this,
        categories = this.data.categories = new Categories();

      this.loading = true;
      categories.showHidden = true;

      this.collection = new ForecastEntries();
      this.model = new ForecastEntry();

      Promise.all([this.collection.fetch(), categories.fetch()]).then(function () {
        _this.loading = false;

        if (entryId != 'new') {
          _this.model = _this.collection.get(entryId);
          _this.data.title = 'Editar Item do Orçamento';
        } else {
          _this.data.title = 'Novo Item do Orçamento';
        }

        _this.render();
      });
    },

    addMonthlyAmount: function (e) {
      var lastMonthlyAmount,
        newAmount,
        newMonth,
        newYear,
        today = new Date(),
        monthlyAmounts = this.model.get('monthlyAmounts');
      e.preventDefault();
      lastMonthlyAmount = monthlyAmounts[monthlyAmounts.length - 1];
      if (lastMonthlyAmount == null) {
        newAmount = {
          month: today.getMonth() + 1,
          year: today.getFullYear(),
          amount: 0
        };
      } else {
        newMonth = lastMonthlyAmount.month + 1;
        newYear = lastMonthlyAmount.year;
        if (newMonth > 12) {
          newMonth = 1;
          newYear++;
        }
        newAmount = {
          month: newMonth,
          year: newYear,
          amount: lastMonthlyAmount.amount
        };
      }
      monthlyAmounts.push(newAmount);
      this.render();
    },

    getMonthlyAmountsFromForm: function () {
      var amounts = [];
      $('table.monthly tbody tr').each(function (index, row) {
        var $row = $(row);
        amounts.push({
          month: parseInt($row.find('select.month').val(), 10),
          year: parseInt($row.find('input.year').val(), 10),
          amount: parseFloat($row.find('input.amount').val().replace(/,(\d+)$/g,'.$1'))
        });
      });
      return amounts;
    },

    goToAfterSave: function () {
      return "/forecast";
    },

    monthlyAmountsChanged: function () {
      var amountsInForm = this.getMonthlyAmountsFromForm();
      this.model.set('monthlyAmounts', amountsInForm);
    },

    processData: function (data) {
      data.monthlyAmounts = this.getMonthlyAmountsFromForm();
      return data;
    },

    removeMonthlyAmount: function (e) {
      e.preventDefault();
      $(e.target).parents('tr').remove();
      this.monthlyAmountsChanged();
    }
  });
});