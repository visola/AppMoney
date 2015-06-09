define(["react", "collection/Accounts", "jsx!component/account/ListAccounts"],
  function (React, Accounts, ListAccounts) {

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

      render : function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return <ListAccounts
                   accounts={this.state.accounts.models}
                   onDeleteAccount={this.handleDeleteAccount}
                 />;
        }
      }
    });

});
