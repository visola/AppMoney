define(["react", "router", "collection/Accounts", "model/Transaction", "collection/Categories", "jsx!component/transaction/Edit"],
  function (React, Router, Accounts, Transaction, Categories, EditTransaction) {

    return React.createClass({
      componentDidMount: function () {
        var _this = this;

        Promise.all([this.state.accounts.fetch(), this.state.categories.fetch()])
          .then(function () {
            _this.setState({loading:false});
          });
      },

      getInitialState: function () {
        return {
          accounts: new Accounts(),
          categories: new Categories(),
          loading: true,
          transaction: this.props.transaction || new Transaction()
        };
      },

      handleSave: function (data) {
        var transaction = this.state.transaction,
          value = data.value;

        for (var name in data) {
          transaction.set(name, data[name]);
        }

        value = value.replace(/,/g,'.'); // replace comma by dot
        value = Math.abs(parseFloat(value));

        if (!this.props.credit) {
          value = -1 * value;
        }
        transaction.set('value', value);

        transaction.set('fromAccountId', this.props.fromId);
        transaction.save({
            error: function (model, response) {
                alert("An error happened while saving the transaction.")
            }
        });
        Router.navigate('/', {trigger:true});
      },

      render: function () {
        if (this.state.loading) {
          return <p>Loading...</p>;
        } else {
          return <EditTransaction
                   account={this.state.accounts.get(this.props.fromId)}
                   categories={this.state.categories}
                   onSave={this.handleSave}
                   transaction={this.state.transaction}
                 />
        }
      }
    });

});