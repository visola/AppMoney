define(["react"], function (React) {
  var BaseControl = {
    getDefaultProps: function() {
      return {
        fieldSize: 10,
        labelSize: 2,
        onChange: function (){},
        type: 'text'
      };
    },
    getValue: function () {
      return this.refs['comp'].getDOMNode().value;
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
          <input className="form-control" ref="comp" {...this.props} />
        </FormControl>
      );
    }
  });

  var Select = React.createClass({
    mixins: [BaseControl],

    render: function () {
      return (
        <FormControl fieldSize={this.props.fieldSize} label={this.props.label} labelSize={this.props.labelSize}>
          <select className="form-control" ref="comp" {...this.props}>
            {this.props.children}
          </select>
        </FormControl>
      );
    }
  });
 
  return {
    Form:Form,
    Input:Input,
    Select:Select
  };

});