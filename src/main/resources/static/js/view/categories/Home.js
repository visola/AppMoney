define(['jquery', 'view/Base', 'bootstrap', 'bootstrap-modal', 'view/categories/Edit', 'collection/Categories', 'tpl!template/categories/home.html'],
    function($, BaseView, Bootstrap, BootstrapModal, EditCategoryView, Categories, CategoriesHomeTemplate) {

  var HomeView = BaseView.extend({
    template: CategoriesHomeTemplate,
    events: {
      'click #new-category' : 'create',
      'keyup #search-categories' : 'search'
    },

    create: function () {
      new Backbone.BootstrapModal(new EditCategoryView(this.collection).getModalOptions()).open();
    },

    initialize: function () {
      var _this = this;

      this.data.filtered = [];
      this.data.query = '';

      this.collection = new Categories();
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

      if (this.data.query == value) return;

      if (value) {
        this.data.filtered = this.collection.filter(function (el, index) {
          return el.get('name').toLowerCase().indexOf(value.toLowerCase()) >= 0;
        });
      } else {
        this.data.filtered = this.collection.slice(0, this.collection.length);
      }
      this.data.query = value;
      this.render();

      searchBox = $('#search-categories')[0];
      searchBox.selectionStart = value.length;
      searchBox.selectionStart = value.length;
      searchBox.focus();
    }
  });

  return HomeView;
});