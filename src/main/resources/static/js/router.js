define(["jquery", "backbone", "security", "tpl!template/menu.html"], 
  function ($, Backbone, Security, MenuTemplate) {
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
        "reports/category" : "categoryReport",
        "forecast(/)" : "forecast",
        "forecast_entries/:id(/)": "forecastEntries"
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
        let menuElement = document.getElementById('menu');
        if (menuElement) {
          menuElement.innerHTML = '';
        }
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

      forecast: function () {
        require(['view/forecast/Home'], function (ForecastHomeView) {
          render(new ForecastHomeView());
        });
      },

      forecastEntries: function (id) {
        require(['view/forecast/entries/Edit'], function (EditForecastEntryView) {
          render(new EditForecastEntryView(id));
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
        var activePath = window.location.pathname,
          items = [
              {title:'Home', link:'/'},
              {title:'Categorias', link:'/categories'},
              {title:'Orçamento', link:'/forecast'},
              {title:'Relatório', link:'/reports/category'}];

        document.getElementById('menu-bar').innerHTML = MenuTemplate({activePath: window.location.pathname, items: items});
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
