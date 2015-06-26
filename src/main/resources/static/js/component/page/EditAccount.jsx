define(["react", "collection/Accounts", "jsx!component/account/Edit"],
  function (React, Accounts, Edit) {

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

      handleUpdateAccount: function (account) {
        account.save({
            error: function (model, response) {
                alert("An error happened while updating your account.")
            }
        });
        this.forceUpdate();
      },

      render : function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return <Edit 
                  account={this.state.accounts.get(this.props.accountId)} 
                  onUpdateAccount={this.handleUpdateAccount}
                 />;
        }
      }
    });

});