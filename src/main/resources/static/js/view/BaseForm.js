define(['underscore', 'jquery', 'backbone', 'router', 'view/Base'], 
    function (_, $, Backbone, router, BaseView) {

  var BaseFormView = BaseView.extend({
    data: {},

    /**
     * Handle form submissions.
     */
    events: {
      'submit form' : '__handleSave'
    },

    /**
     * Fetches data from all inputs in this view and return an object
     * mapping the input value to the element name attribute.
     * 
     * @parm formSelector (String) A selector to specify what form data
     *       should be loaded from. Defaults to <code>form</code>.
     */
    getFormData: function (formSelector) {
      var data = {};

      if (!formSelector) {
        formSelector = 'form';
      }

      this.$(formSelector).find('input, textarea, select').each(function (index, el) {
        var $el = $(el);
        data[$el.attr('name')] = $el.val();
      });

      return data;
    },

    /**
     * Gives subviews a chance to process the data from the form and then
     * saves the data using <code>this.model</code>.
     */
    __handleSave: function (e) {
      var data = this.getFormData(),
        submitButton = this.$('button[type=submit]'),
        originalText = submitButton.html();

      if (e && e.preventDefault) {
        e.preventDefault();
      }

      submitButton.attr('disabled', 'disabled')
      data = this.processData(data);

      this.model.save(data, {
        wait: true,
        success: function() {
          alert("Data saved successfully!");
          router.navigate('/', {trigger:true});
        },
        error: function () {
          submitButton.html(originalText);
          submitButton.removeAttr('disabled');
          console.error(arguments);
          alert("Sorry, an error happend. Please try again later.");
        }
      });
    },

    processData: function (data) {
      return data;
    }
  });

  BaseFormView.extend = function (child) {
    var view = BaseView.extend.apply(this, arguments);
    view.prototype.events = _.extend({}, this.prototype.events, child.events);
    return view;
  };

  return BaseFormView;
});