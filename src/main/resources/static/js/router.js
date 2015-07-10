define(["jquery", "backbone", "react", "security"], 
  function ($, Backbone, React, Security) {
    var originalRoute = Backbone.Router.prototype.route;

    function getContentElement() {
      return document.getElementById('content');
    };

    function render(view) {
      view.render();
      $('#content').html(view.$el);
    };

    var Router = Backbone.Router.extend({
      routes : {
        "(/)" : "home",
        "login(/)" : "login",
        "accounts/:id(/)" : "editAccount",
        "debit/:fromId(/)" : "debitAccount",
        "credit/:fromId(/)" : "creditAccount"
      },

      _createTransaction: function (toId, credit) {
        require(["view/transaction/Edit"], function (EditTransactionView) {
          render(new EditTransactionView(toId, credit));
        });
      },

      creditAccount: function (toId) {
        this._createTransaction(toId, true);
      },

      debitAccount: function (toId) {
        this._createTransaction(toId, false);
      },

      editAccount: function (id) {
        require(['view/account/Edit'], function (EditAccountView) {
          render(new EditAccountView(id));
        });
      },

      home : function () {
        require(['view/Home'], function (HomeView) {
          render(new HomeView());
        });
      },

      login : function () {
        require(["tpl!template/login.html"], function (LoginTemplate) {
          getContentElement().innerHTML = LoginTemplate();
        });
      },

      route : function (route, name, callback) {
        if (Security.isLoggedIn()) {
          originalRoute.call(this, route, name, callback);
        } else {
          this.login();
        }
      }
    });

    return new Router();
  }
);
