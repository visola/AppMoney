define(["jquery", "backbone", "security", "tpl!template/menuItem.html"], 
  function ($, Backbone, Security, MenuItemTemplate) {
    var originalRoute = Backbone.Router.prototype.route;

    function getContentElement() {
      return document.getElementById('content');
    };

    function render(view) {
      view.render();
      $(getContentElement()).html(view.$el);
    };

    var Router = Backbone.Router.extend({
      routes : {
        "(/)" : "home",
        "login(/)" : "login",
        "accounts/:id(/)" : "editAccount",
        "debit/:toId(/)" : "debitAccount",
        "credit/:toId(/)" : "creditAccount",
        "transactions/:id(/)" : "editTransaction",
        "transfer/:toId/:fromId(/)" : "transfer",
        "categories": "categories",
        "reports/category" : "categoryReport"
      },

      categories: function () {
        require(["view/categories/Home"], function (CategoriesHomeView) {
          render(new CategoriesHomeView());
        });
      },

      categoryReport: function () {
        require(['view/report/Category'], function (CategoryReportView) {
          render(new CategoryReportView());
        });
      },

      clearMenu: function () {
        document.getElementById('menu').innerHTML = '';
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

      editTransaction: function (transactionId) {
        require(['view/transaction/Edit', 'model/Transaction'], function (EditTransactionView, Transaction) {
          var transaction = new Transaction({id:transactionId});
          transaction.fetch().then(function () {
            render(new EditTransactionView({model:transaction}));
          });
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

      renderMenu: function () {
        var i,
          items = [
              {title:'Home', link:'/'},
              {title:'Categories', link:'/categories'},
              {title:'Report', link: '/reports/category'}],
          menuItem = '';

        for (i = 0; i < items.length; i++) {
          menuItem += MenuItemTemplate({
            active: false,
            title:items[i].title,
            link:items[i].link
          });
        }

        document.getElementById('menu').innerHTML = menuItem;
      },

      route : function (route, name, callback) {
        if (Security.isLoggedIn()) {
          getContentElement().innerHTML = '<p>Carregando...</p>';
          originalRoute.call(this, route, name, callback);
          this.renderMenu();
        } else {
          this.login();
          this.clearMenu();
        }
      },

      transfer: function (toAccountId, fromAccountId) {
        require(["view/transaction/Edit"], function (EditTransactionView) {
          render(new EditTransactionView(toAccountId, fromAccountId));
        });
      }
    });

    return new Router();
  }
);
