define(['jquery', 'view/Base', 'bootstrap', 'bootstrap-modal', 'view/categories/Edit', 'collection/Categories', 'tpl!template/categories/home.html'],
    function($, BaseView, Bootstrap, BootstrapModal, EditCategoryView, Categories, CategoriesHomeTemplate) {

  var HomeView = BaseView.extend({
    template: CategoriesHomeTemplate,
    events: {
      'click .edit-category' : 'editCategory',
      'click #new-category' : 'create',
      'keyup #search-categories' : 'search'
    },

    create: function () {
      let modal = new Backbone.BootstrapModal(new EditCategoryView(this.collection).getModalOptions());
      modal.open();
      modal.$el.on('hidden.bs.modal', this.load.bind(this));
    },

    editCategory: function (e) {
      var $t = $(e.target),
        categoryId = $t.parent().parent().attr('id').split('-')[1],
        category = this.collection.get(categoryId),
        modal = new Backbone.BootstrapModal(new EditCategoryView(this.collection, category).getModalOptions());
      modal.open();
      modal.$el.on('hidden.bs.modal', this.load.bind(this));
    },

    filter: function (value, force) {
      if (!force && this.data.query == value) return;

      if (value) {
        this.data.filtered = this.collection.filter(function (el, index) {
          if (el.get('owner').username.toLowerCase().indexOf(value.toLowerCase()) >= 0) {
            return true;
          }
          return el.getLeafName().toLowerCase().indexOf(value.toLowerCase()) >= 0;
        });
      } else {
        this.data.filtered = this.collection.slice(0, this.collection.length);
      }
      this.data.query = value;
    },

    initialize: function () {
      this.data.filtered = [];
      this.data.query = '';

      this.load();
    },

    load: function () {
      this.loading = true;
      this.render();

      this.collection = new Categories();
      this.collection.fetch().then(() => {
        this.loading = false;
        this.data.filtered = this.collection.slice(0, this.collection.length);
        this.render();
      });
    },

    search: function (e) {
      var searchBox,
        value = $(e.target).val();

      this.filter(value);
      this.render();

      searchBox = $('#search-categories')[0];
      searchBox.selectionStart = value.length;
      searchBox.selectionStart = value.length;
      searchBox.focus();
    }
  });

  return HomeView;
});