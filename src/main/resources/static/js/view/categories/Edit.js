define(['view/BaseForm', 'model/Category', 'tpl!template/categories/edit.html'],
    function (BaseFormView, Category, EditCategoryTemplate) {

  var EditCategoryView = BaseFormView.extend({
    template: EditCategoryTemplate,
    events: {
      "submit form": "handleOk"
    },

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

    handleOk: function (e) {
      if (e && e.preventDefault) {
        e.preventDefault();
      }

      this.__handleSave();
      this.$el.parents(".modal").modal("hide");
    },

    processData: function (data) {
      if (data.parentId == -1) {
        data.parent = null;
      } else {
        data.parent = {
          id: data.parentId
        }
        delete data.parentId;
      }

      return data;
    }
  });

  return EditCategoryView;

});