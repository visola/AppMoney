define(["react", "router"], function (React, Router) {

    return React.createClass({

      handleClick: function (account, e) {
        e.preventDefault();
        Router.navigate('/accounts/' + account.id, {trigger:true});
      },
 
      render : function () {
        return <ul>{this.renderAccounts()}</ul>;
      },

      renderAccounts: function () {
        var account, i, elements = [];
        for (i = 0; i < this.props.accounts.length; i++) {
          account = this.props.accounts[i];
          elements.push(<li><a href="#" onClick={this.handleClick.bind(null, account)}>{account.id} - {account.get('type')}</a></li>);
        }
        return elements;
      }
    });

});