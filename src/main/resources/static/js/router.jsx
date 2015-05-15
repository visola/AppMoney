define(["backbone", "react", "jsx!component/Home"], 
  function (Backbone, React, Home) {
    var Router = Backbone.Router.extend({
      routes : {
        "(/)" : "home"
      },

      home : function () {
        React.render(<Home />, document.body);
      }
    });

    return new Router();
  }
);