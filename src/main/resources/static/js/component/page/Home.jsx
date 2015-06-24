define(["react", "collection/Accounts", "jsx!component/account/List"],
  function (React, Accounts, List) {

    return React.createClass({
      componentDidMount: function () {
        var _this = this;
        this.state.accounts.fetch().then(function () {
          _this.state.loading = false;
          _this.setState(_this.state);
        });
      },

      getInitialState: function () {
        return {accounts:new Accounts(), loading: true};
      },

      render : function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return <List accounts={this.state.accounts.models} />;
        }
      }
    });

});