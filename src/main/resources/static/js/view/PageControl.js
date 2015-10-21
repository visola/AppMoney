define(['view/Base', 'tpl!template/pageControl.html'], function (BaseView, PageControlTemplate) {

  var PageControlView = BaseView.extend({
    template: PageControlTemplate,
    events: {
      'click a': 'changePage'
    },
    initialize: function (collection, parentView) {
      this.collection = collection;
      this.parentView = parentView;
    },
    changePage: function (e) {
      var $target = this.$(e.target),
        text = $target.text(),
        pageNumber = parseInt(text),
        $parent = $target.parent(),
        callback = this.parentView.render.bind(this.parentView),
        options = {success:callback};

      e.preventDefault();

      if (!$parent.hasClass('active') && !$parent.hasClass('disabled')) {
        if (isNaN(pageNumber)) {
          switch (text) {
            case 'Next':
              this.collection.getNextPage(options);
              break;
            case 'Previous':
              this.collection.getPreviousPage(options);
              break;
            case 'First':
              this.collection.getPage(0, options);
              break;
            case 'Last':
              this.collection.getPage(this.collection.state.totalPages - 1, options);
              break;
          }
        } else {
          this.collection.getPage(pageNumber - 1, options);
        }
      }
    }
  });

  return PageControlView;
});