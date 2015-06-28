define(["react", "router", "collection/Accounts", "jsx!component/account/List"],
  function (React, router, Accounts, ListAccounts) {

    return React.createClass({
      componentDidMount: function () {
        var _this = this;
        this.state.accounts.fetch().then(function () {
          _this.setState({loading:false});
        });
      },

      getInitialState: function () {
        return {accounts:new Accounts(), loading: true};
      },

      handleDeleteAccount: function (account) {
        var _this = this;
        account.destroy({ 
          error: function (model, response) {
            alert("An error happend while deleting your account.");
          }
        });
        this.forceUpdate();
      },

      handleNewAccount: function () {
        router.navigate('/accounts/new',{trigger:true});
      },

      render : function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return (<div>
            <p>
              <a onClick={this.handleNewAccount}>Create Account</a>
            </p>
            <ListAccounts
              accounts={this.state.accounts.models}
              onDeleteAccount={this.handleDeleteAccount}
              />
            </div>);
        }
      }
    });

});
