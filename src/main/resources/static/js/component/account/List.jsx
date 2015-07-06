define(["react", "router"], function (React, Router) {

    return React.createClass({

      handleAddTransaction: function (account, transactionType, e) {
        Router.navigate(transactionType + '/' + account.id, {trigger:true});
      },

      handleSelectAccount: function (account, e) {
        e.preventDefault();
        Router.navigate('/accounts/' + account.id, {trigger:true});
      },

      render : function () {
        if (this.props.accounts.length == 0) {
          return <p>You have no accounts.</p>;
        } else {
          return (<table className="table center">
            <thead>
              <tr>
                <th>Name</th>
                <th>Balance</th>
                <th>Actions</th>
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
          <td><a href="#" onClick={this.handleSelectAccount.bind(null, account)}>{account.get('name')}</a></td>
          <td>$ {account.get('balance') || 0}</td>
          <td>
            <span onClick={this.handleAddTransaction.bind(null, account, 'credit')} className="glyphicon glyphicon-plus btn btn-success btn-sm"></span>
            {' '}
            <span onClick={this.handleAddTransaction.bind(null, account, 'debit')} className="glyphicon glyphicon-minus btn btn-danger btn-sm"></span>
          </td>
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
