define(["react"], function (React) {
  return React.createClass({
    render : function () {
      var href = "/authenticate/google?path="+window.location.pathname;
      return (
        <div className="login">
          <h3>Please login</h3>
          <p>
            <a className="google-button btn" href={href}>Sign in with Google</a>
          </p>
        </div>);
    }
  });
});