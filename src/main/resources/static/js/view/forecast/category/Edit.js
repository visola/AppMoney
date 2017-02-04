define([
        'underscore',
        'view/BaseForm',
        'tpl!template/forecast/category/edit.html',
        'collection/CategoryForecastEntries',
        'collection/Categories',
        'model/CategoryForecastEntry',
        'router'],
    function (
        _,
        BaseFormView,
        EditTemplate,
        CategoryForecastEntries,
        Categories,
        CategoryForecastEntry,
        router) {

  return BaseFormView.extend({
    template: EditTemplate,

    initialize: function (entryId) {
      var _this = this,
        categories = this.data.categories = new Categories();

      this.loading = true;
      categories.showHidden = true;

      this.collection = new CategoryForecastEntries();
      this.model = new CategoryForecastEntry();

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
    }
  });
});