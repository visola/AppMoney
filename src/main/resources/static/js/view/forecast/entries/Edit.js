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
          _this.data.title = 'Editar Orçamento';
        } else {
          _this.data.title = 'Novo Orçamento';
        }

        _this.render();
      });
    },

    goToAfterSave: function () {
      return "/forecast";
    },

    processData: function (data) {
      // replace comma by dot
      data.amount = data.amount.replace(/,(\d+)$/g,'.$1');
      return data;
    }
  });
});