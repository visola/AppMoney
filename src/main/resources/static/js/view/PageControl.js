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
          if (text == 'Next') {
            this.collection.getNextPage(options);
          }
          if (text == 'Previous') {
            this.collection.getPreviousPage(options);
          }
        } else {
          this.collection.getPage(pageNumber - 1, options);
        }
      }
    }
  });

  return PageControlView;
});