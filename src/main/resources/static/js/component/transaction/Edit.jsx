define(["react", "router", "moment", "jsx!component/Bootstrap"], function (React, Router, Moment, Bootstrap) {

  var Form = Bootstrap.Form,
    Input = Bootstrap.Input,
    Select = Bootstrap.Select;

  return React.createClass({

    handleSave: function (e) {
      var data = {};
      e.preventDefault();
      for (var name in this.refs) {
        data[name] = this.refs[name].getValue();
      }
      this.props.onSave(data);
    },

    render : function () {
      var titlePrefix = this.props.credit ? 'Credit account' : 'Debit account';

      return (
      <div className="container">
        <h3>{titlePrefix + ' - ' + this.props.account.get('name')}</h3>
        <Form>
          <Input label="Title:" ref="title"/>
          <Select label="Category:" ref="categoryId">
            <option value="0">-- Select --</option>
            {this.renderCategories()}
          </Select>
          <Input label="Value:" ref="value"/>
          <Input label="Happened:" type="date" defaultValue={Moment(new Date()).format("YYYY-MM-DD")} ref="happened"/>
          <div className="form-group">
            <div className="col-sm-offset-2 col-sm-10">
              <button className="btn btn-default" onClick={this.handleSave}>Save</button>
            </div>
          </div>
        </Form>
      </div>);
    },

    renderCategories: function () {
      var categories = [],
        i,
        models = this.props.categories.models;

      models.sort(function (m1, m2) {
        return m1.get('name').toLowerCase().localeCompare(m2.get('name').toLowerCase());
      });

      for (i = 0; i < models.length; i++) {
        categories.push(this.renderCategory(models[i]));
      }
      return categories;
    },

    renderCategory: function (category) {
      return <option value={category.id}>{category.get('name')}</option>;
    }
  });

});
