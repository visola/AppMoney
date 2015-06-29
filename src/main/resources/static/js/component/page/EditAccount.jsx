define(["react", "collection/Accounts", "model/Account", "jsx!component/account/Edit"],
  function (React, Accounts, Account, Edit) {

    return React.createClass({
      componentDidMount: function () {
        var _this = this;

        if (this.props.accountId == 'new') {
          // Don't need to load all accounts if creating new one
          return;
        }

        this.state.accounts.fetch().then(function () {
          _this.setState({loading:false});
        });
      },

      getInitialState: function () {
        if (this.props.accountId == 'new') {
          return {accounts:null, loading: false};
        } else {
          return {accounts:new Accounts(), loading: true};
        }
      },

      handleUpdateAccount: function (account) {
        account.save({
            error: function (model, response) {
                alert("An error happened while updating your account.")
            }
        });
        this.forceUpdate();
      },

      render : function () {
        var account;

        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          if (this.props.accountId == 'new') {
            account = new Account();
          } else {
            account = this.state.accounts.get(this.props.accountId)
          }

          return <Edit 
                  account={account}
                  onUpdateAccount={this.handleUpdateAccount}
                 />;
        }
      }
    });

});