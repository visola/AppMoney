define(["react", "router"], function (React, Router) {

    return React.createClass({

      handleClick: function (account, e) {
        e.preventDefault();
        Router.navigate('/accounts/' + account.id, {trigger:true});
      },

      render : function () {
        if (this.props.accounts.length == 0) {
          return <p>You have no accounts.</p>;
        } else {
          return (<table className="table">
            <thead>
              <tr><th>Name</th><th>Type</th><th>Balance</th></tr>
            </thead>
            <tbody>
              {this.renderAccounts()}
            </tbody>
           </table>);
        }
      },

      renderAccount: function (account) {
        return (<tr>
          <td><a href="#" onClick={this.handleClick.bind(null, account)}>{account.get('name')}</a></td>
          <td>{account.get('type')}</td>
          <td>$ {account.get('balance') || 0}</td>
        </tr>);
      },

      renderAccounts: function () {
        var account, i, elements = [];
        for (i = 0; i < this.props.accounts.length; i++) {
          elements.push(this.renderAccount(this.props.accounts[i]));
        }
        return elements;
      }
    });

});