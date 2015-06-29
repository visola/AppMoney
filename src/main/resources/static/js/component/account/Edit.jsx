define(["react", "router", "moment", "jsx!component/Bootstrap"], function (React, Router, Moment, Bootstrap) {

  var Form = Bootstrap.Form,
    Input = Bootstrap.Input,
    Select = Bootstrap.Select;

  return React.createClass({

    componentDidMount : function() {
      this.props.account.on("invalid" , function(account, error) {
        alert(account.get('name') + " " + error);
      });
    },

    componentWillUnmount: function () {
      this.props.account.off(null, null, this);
    },

    handleOnChange : function (field, e) {
      e.preventDefault();
      this.props.account.set(field, e.target.value);
    },

    handleUpdate : function (accoundName, e) {
      e.preventDefault();
      if (confirm("You confirm all data to update this account: " + accoundName + "?")) {
        this.props.onUpdateAccount(this.props.account);
      }
    },

    render : function () {
      var title = this.props.account.isNew() ? 'Create new account' : 'Change account data';
      return (
      <div className="container">
        <h2>{title}</h2>
        <Form>
          <Input label="Name:" defaultValue={this.props.account.get('name')} onChange={this.handleOnChange.bind(null, 'name')} />
          <Input label="Initial Balance:" type="number" step="0.01" defaultValue={this.props.account.get('initialBalance') || 0} onChange={this.handleOnChange.bind(null, 'initialBalance')}/>
          <Input type="date" label="Initial Balance Date:" defaultValue={Moment(this.props.account.get('initialBalanceDate')).format("YYYY-MM-DD")} onChange={this.handleOnChange.bind(null, 'initialBalanceDate')}/>
          <Select defaultValue={this.props.account.get('type')} label="Type:" onChange={this.handleOnChange.bind(null, 'type')}>
            <option value="CHECKINGS">Checkings</option>
            <option value="CREDIT_CARD">Credit Card</option>
            <option value="SAVINGS">Savings</option>
            <option value="WALLET">Wallet</option>
          </Select>
          <div className="form-group">
            <div className="col-sm-offset-2 col-sm-10">
              <button onClick={this.handleUpdate.bind(null, this.props.account.get('name'))} type="submit" className="btn btn-default">Save</button>
            </div>
          </div>
        </Form>
      </div>);
    }
  });

});
