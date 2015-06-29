define(["react", "router", "moment"], function (React, Router, Moment) {

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
          <form className="form-horizontal" role="form">
            <div className="form-group">
              <label className="control-label col-sm-2" >Name:</label>
              <div className="col-sm-10">
                <input type="text" className="form-control" id="name" defaultValue={this.props.account.get('name')} onChange={this.handleOnChange.bind(null, 'name')}/>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-sm-2">Initial Balance:</label>
              <div className="col-sm-10">
                <input type="number" step="0.01" className="form-control" id="initialBalance" defaultValue={this.props.account.get('initialBalance') || 0} onChange={this.handleOnChange.bind(null, 'initialBalance')}/>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-sm-2" >Initial Balance Date:</label>
              <div className="col-sm-10">
                <input type="date" className="form-control" id="initialBalanceDate" defaultValue={Moment(this.props.account.get('initialBalanceDate')).format("YYYY-MM-DD")} onChange={this.handleOnChange.bind(null, 'initialBalanceDate')}/>
              </div>
            </div>
            <div className="form-group">
              <label className="control-label col-sm-2">Type:</label>
              <div className="col-sm-10">
                <select className="form-control" id="type" onChange={this.handleOnChange.bind(null, 'type')}>
                  <option value="CHECKINGS">Checkings</option>
                  <option value="CREDIT_CARD">Credit Card</option>
                  <option value="SAVINGS">Savings</option>
                  <option value="WALLET">Wallet</option>
                </select>
              </div>
            </div>     
            <div className="form-group">
              <div className="col-sm-offset-2 col-sm-10">
                <button onClick={this.handleUpdate.bind(null, this.props.account.get('name'))} type="submit" className="btn btn-default">Submit</button>
              </div>
            </div>
          </form>
        </div>);
      }
    });

});