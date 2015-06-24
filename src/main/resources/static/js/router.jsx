define(["backbone", "react", "security"], 
  function (Backbone, React, Security) {
    var originalRoute = Backbone.Router.prototype.route;

    function getContentElement() {
      return document.getElementById('content');
    };

    var Router = Backbone.Router.extend({
      routes : {
        "(/)" : "home",
        "login(/)" : "login",
        "accounts/:id(/)" : "editAccount"
      },

      editAccount: function (id) {
        require(["jsx!component/page/EditAccount"], function (EditAccount) {
          React.render(<EditAccount accountId={id}/>, getContentElement());
        });
      },

      home : function () {
        require(["jsx!component/page/Home"], function (Home) {
          React.render(<Home />, getContentElement());
        });
      },

      login : function () {
        require(["jsx!component/page/Login"], function (Login) {
          React.render(<Login />, getContentElement());
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