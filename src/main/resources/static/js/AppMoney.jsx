require(["jquery", "backbone", "react"], function ($, Backbone, React) {
  var AppMoney = React.createClass({
    render: function () {
      return <h1>Hello world!</h1>;
    }
  });

  $(function () {
    React.render(<AppMoney />, document.body);
  });
});