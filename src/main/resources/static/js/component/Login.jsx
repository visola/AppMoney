define(["react"], function (React) {
  return React.createClass({
    render : function () {
      return (
        <div>
          <h3>Please login</h3>
          <p>
            Your session expired. Please login again: <br />
            <a href="/authenticate/google">Login with Google</a>
          </p>
        </div>);
    }
  });
});