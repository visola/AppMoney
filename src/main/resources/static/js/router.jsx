define(["backbone", "react", "security"], 
  function (Backbone, React, Security) {
    var originalRoute = Backbone.Router.prototype.route;

    var Router = Backbone.Router.extend({
      routes : {
        "(/)" : "home",
        "/login(/)" : "login"
      },

      home : function () {
        require(["jsx!component/Home"], function (Home) {
          React.render(<Home />, document.body);
        });
      },

      login : function () {
        require(["jsx!component/Login"], function (Login) {
          React.render(<Login />, document.body);
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