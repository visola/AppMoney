define(["react"], function (React) {
  return React.createClass({
    render : function () {
      return (
        <div className="login">
          <h3>Please login</h3>
          <p>
            <a className="google-button btn" href="/authenticate/google">Sign in with Google</a>
          </p>
        </div>);
    }
  });
});