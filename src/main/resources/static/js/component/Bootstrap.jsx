define(["react"], function (React) {
  var BaseControl = {
    getDefaultProps: function() {
      return {
        fieldSize: 10,
        labelSize: 2,
        onChange: function (){},
        type: 'text'
      };
    }
  };

  var Form = React.createClass({
    render: function () {
      return <form className="form-horizontal" role="form" {...this.props}>
        {this.props.children}
      </form>;
    }
  });

  var FormControl = React.createClass({
    render: function () {
      return (
        <div className="form-group">
          <label className={'control-label col-sm-'+this.props.labelSize} >{this.props.label}</label>
          <div className={'col-sm-'+this.props.fieldSize}>
            {this.props.children}
          </div>
        </div>
      );
    }
  });

  var Input = React.createClass({
    mixins: [BaseControl],
    render: function () {
      return (
        <FormControl fieldSize={this.props.fieldSize} label={this.props.label} labelSize={this.props.labelSize}>
          <input className="form-control" {...this.props} />
        </FormControl>
      );
    }
  });

  var Select = React.createClass({
    mixins: [BaseControl],

    render: function () {
      return (
        <FormControl fieldSize={this.props.fieldSize} label={this.props.label} labelSize={this.props.labelSize}>
          <select className="form-control" {...this.props}>
            {this.renderOptions()}
          </select>
        </FormControl>
      );
    },

    renderOption: function (option) {
      if (option.type != 'option') {
        throw new Error('Select component only accepts "option" as children');
      }
      var props = option.props;
      return <option value={props.value}>{props.children}</option>
    },

    renderOptions: function () {
      var options = this.props.children,
        result = [];
      for (var i = 0; i < options.length; i++) {
        result.push(this.renderOption(options[i]));
      }
      return result;
    }
  });
 
  return {
    Form:Form,
    Input:Input,
    Select:Select
  };

});