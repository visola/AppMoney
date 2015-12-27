define(['jquery', 'view/Base', 'bootstrap', 'bootstrap-modal', 'view/categories/Edit', 'collection/Categories', 'tpl!template/categories/home.html'],
    function($, BaseView, Bootstrap, BootstrapModal, EditCategoryView, Categories, CategoriesHomeTemplate) {

  var HomeView = BaseView.extend({
    template: CategoriesHomeTemplate,
    events: {
      'change #show-hidden' : 'showHidden',
      'click .edit-category' : 'editCategory',
      'click #hide-all-default' : 'hideAllDefault',
      'click #new-category' : 'create',
      'click .toggle-default' : 'toggleDefault',
      'keyup #search-categories' : 'search',
      'click #show-all-default' : 'showAllDefault'
    },

    create: function () {
      new Backbone.BootstrapModal(new EditCategoryView(this.collection).getModalOptions()).open();
    },

    editCategory: function (e) {
      var $t = $(e.target),
        categoryId = $t.parent().parent().attr('id').split('-')[1],
        category = this.collection.get(categoryId);
      new Backbone.BootstrapModal(new EditCategoryView(this.collection, category).getModalOptions()).open();
    },

    filter: function (value, force) {
      if (!force && this.data.query == value) return;

      if (value) {
        this.data.filtered = this.collection.filter(function (el, index) {
          return el.getLeafName().toLowerCase().indexOf(value.toLowerCase()) >= 0;
        });
      } else {
        this.data.filtered = this.collection.slice(0, this.collection.length);
      }
      this.data.query = value;
    },

    hideAllDefault: function () {
      this.collection.forEach(function (c) {
        if (c.get('createdBy') === null) {
          c.set('hidden', true);
        }
      });
      this.collection.save().then(this.render.bind(this));
    },

    initialize: function () {
      var _this = this;

      this.data.filtered = [];
      this.data.query = '';
      this.data.showHidden = false;

      this.collection = new Categories();
      this.collection.showHidden = true;

      this.loading = true;

      this.collection.fetch().then(function () {
        _this.loading = false;
        _this.data.filtered = _this.collection.slice(0, _this.collection.length);
        _this.render();
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
    },

    showAllDefault: function () {
      this.collection.forEach(function (c) {
        if (c.get('createdBy') === null) {
          c.set('hidden', false);
        }
      });
      this.collection.save().then(this.render.bind(this));
    },

    showHidden: function () {
      this.data.showHidden = !this.data.showHidden;
      this.render();
    },

    toggleDefault: function (e) {
      var $t = $(e.target),
        categoryId = $t.parent().parent().attr('id').split('-')[1],
        category = this.collection.get(categoryId);
      category.set('hidden', !category.get('hidden'));
      category.save({wait:true}).then(this.render.bind(this));
    }
  });

  return HomeView;
});