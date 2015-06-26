define(["react", "router"], function (React, Router) {

    return React.createClass({

      handleClick: function (account, e) {
        e.preventDefault();
        Router.navigate('/accounts/' + account.id, {trigger:true});
      },

      handleDelete: function (account, e) {
        e.preventDefault();
        if (confirm("Are you sure you want to delete account: " + account.get('name') + "?")) {
          this.props.onDeleteAccount(account);
        }
      },
 
      render : function () {
        if (this.props.accounts.length == 0) {
          return <p>You have no accounts.</p>;
        } else {
          return (<table className="table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Initial Balance</th>
                <th>Balance</th>
                <th>&nbsp;</th>
              </tr>
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
          <td>$ {account.get('initialBalance') || 0}</td>
          <td>$ {account.get('balance') || 0}</td>
          <td><a onClick={this.handleDelete.bind(null, account)} className="glyphicon glyphicon-trash btn btn-danger"></a></td>
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
