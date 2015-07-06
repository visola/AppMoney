define(["react", "router", "collection/Accounts", "jsx!component/account/List"],
  function (React, router, Accounts, ListAccounts) {

    return React.createClass({
      componentDidMount: function () {
        var _this = this;
        this.state.accounts.fetch().then(function () {
          _this.setState({loading:false});
          _this.forceUpdate();
        });
      },

      getInitialState: function () {
        return {accounts:new Accounts(), loading: true};
      },

      handleNewAccount: function () {
        router.navigate('/accounts/new',{trigger:true});
      },

      render : function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return (<div>
            <h3>Accounts <small><a onClick={this.handleNewAccount}>Create Account</a></small></h3>
            <ListAccounts
              accounts={this.state.accounts.models}
              />
            </div>);
        }
      }
    });

});
