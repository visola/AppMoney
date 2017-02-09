define(['view/BaseForm', 'model/Category', 'tpl!template/categories/edit.html'],
    function (BaseFormView, Category, EditCategoryTemplate) {

  return BaseFormView.extend({
    template: EditCategoryTemplate,

    initialize: function (collection, category) {
      this.collection = collection;
      this.model = category || new Category();

      this.bind('ok', this.handleOk);
    },

    getModalOptions: function () {
      return {
        content: this,
        title: (this.model.id ? "Editar" : "Criar") + " Categoria"
      };
    },

    goToAfterSave: function () {
      return "/categories";
    },

    handleOk: function () {
      this.__handleSave();
      this.$el.modal('hide');
    },

    processData: function (data) {
      if (data.parentId == -1) {
        data.parentId = null;
      }

      return data;
    }
  });

});